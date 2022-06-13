package com.algaworks.algafoods.api.model;

import java.math.BigDecimal;

import com.algaworks.algafoods.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {
	
	@ApiModelProperty(example="1")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;
	
	@ApiModelProperty(example="Bela Bel")
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})// Agora anota lá no controller
	private String nome;
	
	@ApiModelProperty(example="12.56")
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal precoFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	
	@ApiModelProperty(example="true")
	private boolean ativo;
	
	@ApiModelProperty(example="true")
	private boolean aberto;
	private EnderecoModel endereco;

}
