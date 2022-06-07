package com.algaworks.algafoods.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.FotoProdutoModel;
import com.algaworks.algafoods.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler {

	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoModel toModel (FotoProduto fotoProduto) {
		return modelMapper
				.map(fotoProduto, FotoProdutoModel.class);
	}
	
	
}
