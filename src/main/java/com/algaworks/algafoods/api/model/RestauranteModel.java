package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {
	
	private Long id;
	private String nome;
	private BigDecimal precoFrete;
	private CozinhaModel cozinha;
	private boolean ativo;
	private boolean aberto;
	private EnderecoModel endereco;

}
