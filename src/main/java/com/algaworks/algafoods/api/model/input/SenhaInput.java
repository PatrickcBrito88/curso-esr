package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaInput {

	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String senhaNova;
}
