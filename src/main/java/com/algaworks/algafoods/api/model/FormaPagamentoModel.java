package com.algaworks.algafoods.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {

	@ApiModelProperty(value="ID da forma de pagamento", example = "1")
	private Long id;
	
	@ApiModelProperty(value="Descrição da Forma de Pagamento", example = "Cartão de Débito")
	private String descricao;
}
