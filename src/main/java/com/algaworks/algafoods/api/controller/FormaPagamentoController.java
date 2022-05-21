package com.algaworks.algafoods.api.controller;

import java.util.List;
import java.util.Optional;

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

import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafoods.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formapagamento")
public class FormaPagamentoController {

	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamento;

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody FormaPagamento formaPagamento) {
		formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
		return ResponseEntity.status(HttpStatus.OK).body(formaPagamento);
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			List<FormaPagamento> lista = cadastroFormaPagamento.listar();
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		try {
			FormaPagamento formaPagamento = cadastroFormaPagamento.buscar(id);
			return ResponseEntity.status(HttpStatus.OK).body(formaPagamento);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> atualiza(@PathVariable("id") Long id, @RequestBody FormaPagamento formaPagamento) {
		try {
			formaPagamento = cadastroFormaPagamento.atualizar(id, formaPagamento);
			return ResponseEntity.status(HttpStatus.OK).body(formaPagamento);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> remove(@PathVariable("id") Long id) {
		try {
			cadastroFormaPagamento.apagar(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
