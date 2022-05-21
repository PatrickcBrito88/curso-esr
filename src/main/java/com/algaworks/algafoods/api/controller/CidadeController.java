package com.algaworks.algafoods.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.CidadeModelAssembler;
import com.algaworks.algafoods.api.assembler.CidadeModelDisassembler;
import com.algaworks.algafoods.api.exceptionhandler.Problem;
import com.algaworks.algafoods.api.model.CidadeModel;
import com.algaworks.algafoods.api.model.input.CidadeInput;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafoods.domain.exception.NegocioException;
import com.algaworks.algafoods.domain.model.Cidade;
import com.algaworks.algafoods.domain.repository.CidadeRepository;
import com.algaworks.algafoods.domain.service.CadastroCidadeService;


@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeModelDisassembler cidadeModelDisassembler;
	
	
	@GetMapping
	public List<CidadeModel> listar() {
		 return cidadeModelAssembler.toCollectModel(cidadeRepository.findAll());
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable("cidadeId") Long id) {
		return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(id));
	}


	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {
		try { 
			Cidade cidade = cidadeModelDisassembler.toObjectModel(cidadeInput);
			return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException (e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable("cidadeId") Long id, 
			@RequestBody @Valid CidadeInput cidadeInput) {
		
			try {
				Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
				
				cidadeModelDisassembler.copyToObjectModel(cidadeInput, cidadeAtual);
//				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				
				return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
			} catch (EstadoNaoEncontradoException e) {
				throw new NegocioException(e.getMessage(), e);
			}
		
	}

	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable("cidadeId") Long id) {
		cadastroCidade.remover(id);
	}
	
}
