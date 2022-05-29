package com.algaworks.algafoods.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {

	@NotNull
	private String descricao;
	
}
