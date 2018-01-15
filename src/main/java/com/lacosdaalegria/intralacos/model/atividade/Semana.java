package com.lacosdaalegria.intralacos.model.atividade;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
public class Semana {
	
	private Long id;
	private Date criacao = new Date();
	private Integer semana;
	private Integer ano;
	private Integer status = 1;
	
	public Semana() {
		
		super();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(criacao);
		
		semana = cal.get(Calendar.WEEK_OF_YEAR);
		ano = cal.get(Calendar.YEAR);
		
	}
	
	
	/*
	 * ======================================================================================
	 * ============================== Getters and Setters ===================================
	 * ======================================================================================
	 */
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCriacao() {
		return criacao;
	}
	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}
	@NotNull
	public Integer getSemana() {
		return semana;
	}
	public void setSemana(Integer semana) {
		this.semana = semana;
	}
	@NotNull
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
