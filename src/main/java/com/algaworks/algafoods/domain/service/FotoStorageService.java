package com.algaworks.algafoods.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface FotoStorageService {
	
	//Métodos implementados no LocalFotoStorageService

	void armazenar(NovaFoto novaFoto);
	
	InputStream recuperar (String nomeArquivo);
	
	void remover (String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);//Já armazena a nova foto
		
		if (nomeArquivoAntigo!=null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	//Gera o nome de um novo arquivo
	default String gerarNomeArquivo (String nomeOriginal) {
		return UUID.randomUUID().toString()+"_"+nomeOriginal;
	}
	
	@Getter
	@Builder
	class NovaFoto {
		private String nomeArquivo;
		private InputStream inputStream;
		private String contentType;
		//A partir de um InputStream conseguimos salvar a foto
	}
	
}
