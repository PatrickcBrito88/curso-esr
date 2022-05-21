package com.algaworks.algafoods.api.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algafoods.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafoods.api.assembler.CozinhaModelDisassembler;
import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.input.CozinhaInput;
import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.repository.CozinhaRepository;
import com.algaworks.algafoods.domain.service.CadastroCozinhaService;

//@Controller
//@ResponseBody //Indica que a resposta dos métodos devem ir para a resposta HTTP
@RestController //Substitui o @Controller e o @ResponseBody
//@RequestMapping (value="cozinhas", produces = MediaType.APPLICATION_JSON_VALUE) -- Aplica na classe inteira
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	@Autowired
	private CozinhaModelDisassembler cozinhaModelDisassembler;
		
	
	@GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)//Significa que esse método produz algo
	public List<CozinhaModel> listar(){
		return cozinhaModelAssembler.toCollectModel(cozinhaRepository.findAll());
	}
	
	//@ResponseStatus(HttpStatus.CREATED)//Define o código de status de retorno - Forma mais manual=,,,,,,
	@GetMapping("/{cozinhaId}")
	public CozinhaModel buscar(@PathVariable("cozinhaId")Long id) {
		return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel adicionar (@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaModelDisassembler.toDomainObject(cozinhaInput);
		
		return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinha));	
	}
	
	
	@PutMapping("/{cozinhaId}")
	public CozinhaModel atualizar (@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInput cozinhaInput){
			Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
			
			cozinhaModelDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
			
//			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			return cozinhaModelAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}
	
		
	//2ª Forma de fazer
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
	

}
