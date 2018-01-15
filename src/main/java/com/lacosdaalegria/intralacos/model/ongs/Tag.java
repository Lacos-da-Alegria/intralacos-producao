package com.lacosdaalegria.intralacos.model.ongs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
public class Tag {

	private Long id;
	private Integer tipo;
	private String nome;
	private Integer status = 1;
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
	
	/*
	 * ======================================================================================
	 * ============================== Getters and Setters ===================================
	 * ======================================================================================
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Lob
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
