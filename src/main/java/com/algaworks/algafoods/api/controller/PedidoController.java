package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.PedidoModelAssembler;
import com.algaworks.algafoods.api.assembler.PedidoModelDisassembler;
import com.algaworks.algafoods.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafoods.api.model.PedidoModel;
import com.algaworks.algafoods.api.model.PedidoResumoModel;
import com.algaworks.algafoods.api.model.input.PedidoInput;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.repository.PedidoRepository;
import com.algaworks.algafoods.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private EmissaoPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoModelDisassembler pedidoModelDisassembler;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PedidoResumoModel> listar (){
		return pedidoResumoModelAssembler
				.toCollectModel(pedidoRepository.findAll());
	}
	
	@GetMapping("/{codigoPedido}")
	@ResponseStatus(HttpStatus.OK)
	public PedidoModel buscar (@PathVariable String codigoPedido) {
		return pedidoModelAssembler
				.toModel(cadastroPedidoService.buscarOuFalhar(codigoPedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel salvar (@RequestBody @Valid PedidoInput pedidoInput) {
		Pedido pedido = pedidoModelDisassembler.toObjectModel(pedidoInput);
		pedido.setCliente(new Usuario());
		pedido.getCliente().setId(1L);
		return pedidoModelAssembler.toModel(cadastroPedidoService.salvar(pedido));
	}
	

}
