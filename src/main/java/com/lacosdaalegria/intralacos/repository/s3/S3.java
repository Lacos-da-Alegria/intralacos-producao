package com.lacosdaalegria.intralacos.repository.s3;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3 {
	
	private final String bucketName = "bucket-name";
	private BasicAWSCredentials creds = new BasicAWSCredentials("key", "token");
	private AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion("us-west-2").build();

	public String carregaImagem(String tag, int id, MultipartFile imagem){
		
		String nome_imagem = tag + "_" + id + "_" + System.currentTimeMillis();
		if(nome_imagem.length() > 16)
			nome_imagem = nome_imagem.substring(0, 15);
		
		try {
			s3client.putObject(new PutObjectRequest(
			         bucketName, nome_imagem, imagem.getInputStream(), new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nome_imagem;
	}
	
	public String carregaImagem(String tag, String id, MultipartFile imagem){
		
		String nome_imagem = tag + "_" + id + "_" + System.currentTimeMillis();
		if(nome_imagem.length() > 16)
			nome_imagem = nome_imagem.substring(0, 15);
		
		try {
			s3client.putObject(new PutObjectRequest(
			         bucketName, nome_imagem, imagem.getInputStream(), new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nome_imagem;
	}

}
