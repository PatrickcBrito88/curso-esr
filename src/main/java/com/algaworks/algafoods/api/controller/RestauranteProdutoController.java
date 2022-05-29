package com.algaworks.algafoods.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.algaworks.algafoods.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafoods.api.assembler.ProdutoModelDisassembler;
import com.algaworks.algafoods.api.model.ProdutoModel;
import com.algaworks.algafoods.api.model.input.ProdutoInput;
import com.algaworks.algafoods.domain.model.Produto;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.repository.ProdutoRepository;
import com.algaworks.algafoods.domain.service.CadastroProdutoService;
import com.algaworks.algafoods.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoModelDisassembler produtoModelDisassembler;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel salvar (@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = produtoModelDisassembler.toObjectModel(produtoInput);
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		produto.setRestaurante(restaurante);
		return produtoModelAssembler
				.toModel(cadastroProduto.salvar(produto));
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProdutoModel> listar(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		List<Produto> lista = produtoRepository.findByRestaurante(restaurante);
		return produtoModelAssembler
				.toCollectModel(lista);
	}
	
	@GetMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoModel listarPorProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		Produto produto = cadastroProduto.buscarOuFalhar(produtoId, restauranteId);
		return produtoModelAssembler
				.toModel(produto);
	}

	
	

}
