package com.algaworks.algafoods.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafoods.api.model.CozinhaModel;
import com.algaworks.algafoods.api.model.RestauranteModel;
import com.algaworks.algafoods.domain.model.Cozinha;
import com.algaworks.algafoods.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	/*
	 * Bean para fazer injeção de modelmapper com autowired
	 * Uma vez que modelmapper não é uma dependência do Spring
	 */
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		/*customizar um mapeamento de correspondencia
		 * No exemplo abaixo colocamos o taxaFrete no RestauranteModel como preçofrete
		 * O Model mapper não consegue fazer a conversão, então mapeamos conforme abaixo
		 */
		
		modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
			
		
		return modelMapper;
	}

}
