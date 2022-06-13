package com.algaworks.algafoods.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.CidadeModel;
import com.algaworks.algafoods.domain.model.Cidade;

@Component
public class CidadeModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeModel toModel (Cidade cidade) {
		return modelMapper.map(cidade, CidadeModel.class);		
	}
	
	public List<CidadeModel> toCollectModel (List<Cidade> cidades){
		return cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
		/*
		 * Explicação do porquê do .map e do .collect
		 * 
		 * Stream é uma bilbioteca inserida a partir do Java 8
		 * A partir dele podemos chamar métodos intermediários e métodos finais.
		 * O map serve para transformação de dados.
		 * Neste caso específico está transformando cidade em cidadeModel.
		 * A lista de cidades continuará sendo lista de cidades, mas eu preciso retornar uma lisya
		 * de cidades Model.
		 * Desta forma o .map transforma a lista de cidades em cidadesModel
		 * chama o collect e chama o Collectors para transformar o resultado da transformação
		 * em uma nova lista, que serve de retorno para o método. 
		 */
		
		
	}
}
