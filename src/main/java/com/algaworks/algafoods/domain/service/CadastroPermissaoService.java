package com.algaworks.algafoods.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafoods.domain.exception.CadastroPermissaoNaoEncontradaException;
import com.algaworks.algafoods.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoods.domain.model.Permissao;
import com.algaworks.algafoods.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	public List<Permissao> listar(){
		List<Permissao> lista = permissaoRepository.findAll();
		
		if (lista.isEmpty()) {
			throw new CadastroPermissaoNaoEncontradaException("Lista Vazia");
		}
		
		return lista;
	}
	
	public Permissao buscar (Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		
		if (permissao.isEmpty()) {
			throw new CadastroPermissaoNaoEncontradaException(id);
		}
		return permissao.get();
	}
	
	public void remover (Long id) {
		Optional<Permissao> permissao = permissaoRepository.findById(id);
		
		if (permissao.isEmpty()) {
			throw new CadastroPermissaoNaoEncontradaException(id);
		}
		permissaoRepository.deleteById(id);
	}
	
	public Permissao atualizar (Permissao permissaoNova, Long id) {
		Optional<Permissao> permissaoAtual = permissaoRepository.findById(id);
		if (permissaoAtual.isEmpty()) {
			throw new CadastroPermissaoNaoEncontradaException(id);
		}
		BeanUtils.copyProperties(permissaoNova, permissaoAtual.get(),"id");
		return permissaoRepository.save(permissaoAtual.get());
	}
}
