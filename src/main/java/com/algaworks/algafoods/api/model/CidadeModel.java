package com.algaworks.algafoods.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {
	
	private Long id;
	private String nome;
	private EstadoModel estado;
	
	// Alteração para vir apenas o nome do Estado (usado no endpoint do restaurante)
	//Criada a classe CidadeResumoModel
	
}
