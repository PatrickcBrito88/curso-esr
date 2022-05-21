package com.algaworks.algafoods.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.Permissao;
import com.algaworks.algafoods.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

	@Autowired
	CadastroPermissaoService cadastroPermissao;

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<Permissao> lista = cadastroPermissao.listar();
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		try {
			Permissao permissao = cadastroPermissao.buscar(id);
			return ResponseEntity.status(HttpStatus.OK).body(permissao);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<Permissao> salvar(@RequestBody Permissao permissao) {
		permissao = cadastroPermissao.salvar(permissao);
		return ResponseEntity.status(HttpStatus.CREATED).body(permissao);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@RequestBody Permissao permissao, @PathVariable("id") Long id) {
		try {
			permissao = cadastroPermissao.atualizar(permissao, id);
			return ResponseEntity.status(HttpStatus.OK).body(permissao);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> remover (@PathVariable ("id") Long id){
		try {
		   cadastroPermissao.remover(id);
		   return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
