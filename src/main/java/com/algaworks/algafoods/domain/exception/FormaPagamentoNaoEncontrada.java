package com.algaworks.algafoods.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


public class FormaPagamentoNaoEncontrada extends EntidadeNaoEncontradaException {

	private static final String MSG_FORMA_PAGAMENTO_COM_CODIGO = "Não existe forma de pagaento de código %d";
	private static final long serialVersionUID = 1L;
	private static final String MSG_FORMA_PAGAMENTO = "Fora de pagamento não encontrada";
		
	public FormaPagamentoNaoEncontrada () {
		super (MSG_FORMA_PAGAMENTO);
	}
	
//	public FormaPagamentoNaoEncontrada (Long formaId) {
//		this(String.format(MSG_FORMA_PAGAMENTO_COM_CODIGO, formaId));
//	}

}
