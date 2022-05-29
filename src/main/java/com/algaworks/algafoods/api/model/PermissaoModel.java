package com.algaworks.algafoods.api.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoModel {

	
	private Long id;
	private String nome;
	private String descricao;
	
}
