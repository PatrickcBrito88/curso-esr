package com.algaworks.algafoods.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafoods.domain.model.FormaPagamento;


public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

	
}
