package com.algaworks.algafoods.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.ProdutoModel;
import com.algaworks.algafoods.domain.model.Produto;

@Component
public class ProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoModel toModel (Produto produto) {
		return modelMapper
				.map(produto, ProdutoModel.class);
	}
	
	public List<ProdutoModel> toCollectModel (Collection<Produto>produtos){
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
	}
}
