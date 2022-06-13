package com.algaworks.algafoods.api.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

	@ApiModelProperty(value="Id do Usuário", example = "1")
	private Long id;
	
	@ApiModelProperty(value="Nome do Usuário", example = "Pedro")
	private String nome;
	
	@ApiModelProperty(value="E-mail do usuário", example = "fulano@gmail.com")
	private String email;
	
}
