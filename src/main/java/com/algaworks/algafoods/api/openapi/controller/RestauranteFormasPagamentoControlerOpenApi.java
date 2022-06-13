package com.algaworks.algafoods.api.openapi.controller;

import java.util.List;

import com.algaworks.algafoods.api.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="Restaurantes")
public interface RestauranteFormasPagamentoControlerOpenApi {

	@ApiOperation("Listar formas de pagamento por Id do Restaurante")
	List<FormaPagamentoModel> listar(@ApiParam (value="Id do Restaurante", example = "1", required=true)
		Long restauranteId);
	
	@ApiOperation("Desassociar Forma de Pagamento por Id de Restaurante")
	void desassociar(@ApiParam (value="Id do Restaurante", example = "1", required=true)Long restauranteId,
			@ApiParam (value="Id da Forma de Pagamento", example = "1", required=true)Long formaPagamentoId);
	
	@ApiOperation("Associar Forma de Pagamento por Id do Restaurante")
	void associar(@ApiParam (value="Id do Restaurante", example = "1", required=true)Long restauranteId,
			@ApiParam (value="Id da Forma de Pagamento", example = "1", required=true)Long formaPagamentoId);
}
