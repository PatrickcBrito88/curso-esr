package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.FormaPagamentoNaoEncontrada;
import com.algaworks.algafoods.domain.model.FormaPagamento;
import com.algaworks.algafoods.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	public List<FormaPagamento> listar() {
		List<FormaPagamento> lista = formaPagamentoRepository.findAll();
		if (lista.isEmpty()) {
			throw new FormaPagamentoNaoEncontrada();
		}
		return lista;
	}

	public FormaPagamento buscar(Long id) {
		Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);

		if (formaPagamento.isEmpty()) {
			throw new FormaPagamentoNaoEncontrada();
		}

		return formaPagamento.get();
	}

	public FormaPagamento atualizar(Long id, FormaPagamento formaPagamentoNova) {
		Optional<FormaPagamento> formaPagamentoAtual = formaPagamentoRepository.findById(id);

		if (formaPagamentoAtual.isEmpty()) {
			throw new FormaPagamentoNaoEncontrada();
		}

		formaPagamentoNova.setId(id);
		BeanUtils.copyProperties(formaPagamentoNova, formaPagamentoAtual.get());

		return formaPagamentoRepository.save(formaPagamentoAtual.get());
	}

	public void apagar(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontrada();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Forma de Pagamento de código %d não pode ser removida, " + "pois está em uso", id));
		}
	}

}
