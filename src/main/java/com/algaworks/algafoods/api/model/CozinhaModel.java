package com.algaworks.algafoods.api.model;

import com.algaworks.algafoods.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaModel {
	
	@ApiModelProperty(value="ID da cozinha", example = "1")
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@ApiModelProperty(value="Nome da cozinha", example = "Cozinha Brasileira")
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
}
