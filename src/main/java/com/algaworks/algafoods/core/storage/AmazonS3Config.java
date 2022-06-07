package com.algaworks.algafoods.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {
	
	//Configuração para utilização da API da Amazon
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Bean //Produz instância de amazonS3
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta());
		
		return AmazonS3ClientBuilder.standard()//Criando Builder
				.withCredentials(new AWSStaticCredentialsProvider(credentials))//Passando credenciais
				.withRegion(storageProperties.getS3().getRegiao())//Passando a região
				.build();//constrói
	}

}
