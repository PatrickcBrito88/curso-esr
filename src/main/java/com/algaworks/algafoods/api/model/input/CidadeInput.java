package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
	
	@NotNull
	private String nome;
	
	@NotNull
	private EstadoIdInput estado;
	

}
