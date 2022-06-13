package com.algaworks.algafoods.api.controller;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafoods.api.model.UsuarioModel;
import com.algaworks.algafoods.api.openapi.controller.RestauranteFormasPagamentoControlerOpenApi;
import com.algaworks.algafoods.api.openapi.controller.RestaurantesUsuariosResposanveisControlerOpenApi;
import com.algaworks.algafoods.domain.model.Restaurante;
import com.algaworks.algafoods.domain.model.Usuario;
import com.algaworks.algafoods.domain.service.CadastroRestauranteService;
import com.algaworks.algafoods.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path="/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestaurantesUsuariosResposanveisControlerOpenApi {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UsuarioModel> listaResponsaveis(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return usuarioModelAssembler
				.toCollectModel(restaurante.getUsuariosResponsaveis());	
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar (@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		cadastroRestauranteService.adicionaResponsavel(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar (@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		cadastroRestauranteService.removeResponsavel(restauranteId, usuarioId);
	}
}
