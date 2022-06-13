package com.algaworks.algafoods.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafoods.api.openapi.controller.FluxoControlerOpenApi;
import com.algaworks.algafoods.domain.service.FluxoPedidoService;

@RestController
@RequestMapping(path="/pedidos/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
public class FluxoPedidoController implements FluxoControlerOpenApi{
	
	@Autowired
	private FluxoPedidoService fluxoPedidoService;
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar (@PathVariable String codigoPedido) {
		fluxoPedidoService.confirmar(codigoPedido);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar (@PathVariable String codigoPedido) {
		fluxoPedidoService.cancelar(codigoPedido);
	}
	
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar (@PathVariable String codigoPedido) {
		fluxoPedidoService.entregar(codigoPedido);
	}

}
