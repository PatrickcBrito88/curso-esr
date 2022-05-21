package com.algaworks.algafoods.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(name="subtotal", nullable=false)
	private BigDecimal subtotal;
	
	@Column(name="taxa_frete", nullable=false)
	private BigDecimal taxaFrete;
	
	@Column(name="valor_total", nullable=false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(name="data_criacao", nullable=false)
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	private StatusPedido status;
	
	@OneToMany (mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="usuario_cliente_id", nullable=false)
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(name="restaurante_id", nullable=false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name="forma_pagamento_id", nullable=false)
	private FormaPagamento formaPagamento;
	
	
	
	

}
