package com.lacosdaalegria.intralacos.model.ouvidoria;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
@DynamicUpdate
public class Feedback {

	private Long id;
	private String texto;
	private Integer status = 1;
	private Date criacao = new Date();
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotEmpty
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCriacao() {
		return criacao;
	}
	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}
	
	
	
}
