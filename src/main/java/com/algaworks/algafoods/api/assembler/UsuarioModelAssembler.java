package com.algaworks.algafoods.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafoods.api.model.UsuarioModel;
import com.algaworks.algafoods.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioModel toModel (Usuario usuario) {
		return modelMapper
				.map(usuario, UsuarioModel.class);
	}
	
	public List<UsuarioModel> toCollectModel (Collection<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}
	
	
}
