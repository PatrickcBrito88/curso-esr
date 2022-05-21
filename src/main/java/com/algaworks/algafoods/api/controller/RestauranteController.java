package com.algaworks.algafoods.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafoods.api.assembler.RestauranteModelDisassembler;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.api.model.input.RestauranteInput;
import com.algaworks.algafoods.core.validation.Groups;
import com.algaworks.algafoods.core.validation.ValidacaoException;
import com.algaworks.algafoods.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.repository.RestauranteRepository;
import com.algaworks.algafoods.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteModelDisassembler restauranteModelDisassemler;
	
	@Autowired//A partir de uma instancia que será injetada em validator podemos fazer a validação
	private SmartValidator validator;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel Cadastro(@RequestBody @Valid RestauranteInput restauranteInput){
		//@Validated(Groups.CadastroRestaurante.class)
		// Essa constraint está agrupada no grupo  Cadastro Restaurante 
		//@Valid valida com grupo default
		//@Validated (valida pelo grupo que está marcado
		try {
			Restaurante restaurante = restauranteModelDisassemler.toDomainObject(restauranteInput);//1ª Coversão Representação de entrada para entidade
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));// 2ª conversão Entidade para representaçaõ de saída
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel Atualizar(@PathVariable Long restauranteId, 
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteModelDisassemler.copyToDomainObject(restauranteInput, restauranteAtual);
			
//			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
//					"endereco", "dataCadastro", "produtos");
//			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));			
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}
	}

	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectModel(restauranteRepository.findAll());
	}

	@GetMapping("/{RestauranteID}")
	public RestauranteModel buscar(@PathVariable("RestauranteID") Long id) {
		
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
		RestauranteModel restauranteModel=restauranteModelAssembler.toModel(restaurante);
		
		return restauranteModel;
	}

//	@PatchMapping("/{restauranteId}")
//	public RestauranteModel atualizarParcial(@PathVariable("restauranteId") Long id,
//			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		// o Patch atualiza parcialmente
//		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
//		
//		merge(campos, restauranteAtual, request);
//		
//		//Validar o novo restaurante parcialmente. 
//		validate(restauranteAtual,"restaurante");
//		
//		return Atualiza(id, restauranteAtual);
//
//}
//	
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, 
//				objectName);
//		
//		validator.validate(restaurante, bindingResult);
//		
//		//Ele verifica se tem erros dentro do objeto. Se tiver ele traz
//		if (bindingResult.hasErrors()) {//Tem erro aí dentro ???
//			throw new ValidacaoException(bindingResult); //Aula 9.19
//		}
//		
//	}
//
//	public void merge (Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
//			HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//		//configuração para lançar excessão quando tentar atualizar parcialmente
//		//algo que está sendo ignorado
//		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//		//lança excessão quando tenta jogar um atributo que nem existe na entidade
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//
//		
//		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//		
//		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//			
////			System.out.println(nomePropriedade + "=" + valorPropriedade + " = " + novoValor);
//			
//			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//		
//		});
//		
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//		 throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}
}
