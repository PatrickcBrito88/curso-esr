package com.algaworks.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.api.model.input.RestauranteInput;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	//Para usar o ModelMapper tem que fazer uma configuração que esta la no pacote core
	@Autowired
	private ModelMapper modelMapper;
	
	
	public RestauranteModel toModel (Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
		
	}
	
	public List<RestauranteModel> toCollectModel (List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante ->toModel(restaurante))
				.collect(Collectors.toList());
	}
	
	
	
}
