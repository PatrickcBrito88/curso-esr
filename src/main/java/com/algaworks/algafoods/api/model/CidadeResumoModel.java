package com.algaworks.algafoods.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {
	
	
	private Long id;
	private String nome;
	private String estado;//Dessa forma o ModelMapper pega o id e o nome do Estado (Entao precisei configurar no ModelMapper)
}
