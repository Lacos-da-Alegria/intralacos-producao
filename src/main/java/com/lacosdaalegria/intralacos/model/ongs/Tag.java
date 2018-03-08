package com.lacosdaalegria.intralacos.model.ongs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Integer tipo;
	private String nome;
	private Integer status = 1;

	@Lob
	private String descricao;
	

	public String textoStatus(){
		return (status == 1 ? "Ativo" : "Inativo");
	}
	
	public String textoTipo(){
		if(tipo == 1)
			return "Caracteristica";
		if(tipo == 2)
			return "Atividade";
		else
			return "Ponto Crítico";
	}
	
	public void inverteStatus(){
		if(status == 1)
			status = 0;
		else
			status = 1;
	}

}
