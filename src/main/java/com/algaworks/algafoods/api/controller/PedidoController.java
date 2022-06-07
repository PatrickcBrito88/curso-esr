package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.bytecode.enhance.internal.tracker.SimpleFieldTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.PedidoModelAssembler;
import com.algaworks.algafoods.api.assembler.PedidoModelDisassembler;
import com.algaworks.algafoods.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafoods.api.model.PedidoModel;
import com.algaworks.algafoods.api.model.PedidoResumoModel;
import com.algaworks.algafoods.api.model.input.PedidoInput;
import com.algaworks.algafoods.core.data.PageableTranslator;
import com.algaworks.algafoods.domain.model.Pedido;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.model.filter.PedidoFilter;
import com.algaworks.algafoods.domain.repository.PedidoRepository;
import com.algaworks.algafoods.domain.service.EmissaoPedidoService;
import com.algaworks.algafoods.infrastrucutre.repository.spec.PedidoSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.ImmutableMap;

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

							//Utilizando Filter
//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	public MappingJacksonValue listar (@RequestParam(required = false) String campos){
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectModel(pedidos);  
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if (StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//			//Filtra campos, quebrando tudo em um array, a cada vírgula		
//			//Atenção para usar o filerOutAllExcept com Array
//			//No Postman coloca um Param com nome camos e no valor coloca por exemplo: codigo,valorTotal
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<PedidoResumoModel> pesquisar (@PageableDefault(size=10) Pageable pageable, PedidoFilter pedidoFilter){
		//PedidoFilter especificado lá no Spec
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter),pageable);
		
		List<PedidoResumoModel> pedidosResumoModel = 
				pedidoResumoModelAssembler.toCollectModel(pedidosPage.getContent());
		
		Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());
		
		
		return pedidosResumoModelPage;
		//Para o findAll funcionar com Specification, tem que acrescentar o extends de JPASpecificationExecutor lá no repositório
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
	
	public Pageable traduzirPageable (Pageable pageable) {
		//Aqui iremos traduzir. QUando o consumidor da Api colocar clienteNome no sort. O tradutor 
		//irá converter para cliente.nome
		var mapeamento = ImmutableMap.of(//Tem que colocar todas que deseja que a requisição seja ordenável
				"nomeCliente", "cliente.nome",
				"codigo","codigo",
				"restaurante.nome", "restaurante.nome",
				"valorTotal", "valorTotal"
		);
		
		return PageableTranslator.translate(pageable, mapeamento);
	}
	

}
