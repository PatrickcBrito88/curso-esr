package com.algaworks.algafoods.api.model;

import com.algaworks.algafoods.domain.model.Cidade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeResumoModel cidade;
	
//	public void setNomeEstadoDaCidade(String nome) {
//		cidade.setEstado(nome);
//	}
}
