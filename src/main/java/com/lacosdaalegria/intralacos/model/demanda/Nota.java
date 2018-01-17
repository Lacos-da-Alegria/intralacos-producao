package com.lacosdaalegria.intralacos.model.demanda;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.lacosdaalegria.intralacos.model.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Nota {
	
	private Long id;
	private String texto;
	private Date criacao = new Date();
	private Voluntario criador;
	private Integer status = 1;
	private Integer tipo;
	private Demanda demanda;
	
	/* Tipo de Notas
	 * 0 - Nota
	 * 1 - Pendência
	 * 2 - Resolução de Pendência
	 * 3 - Conclusão
	 * 4 - Reabertura
	 * 5 - Arquivamento
	 */
	
	public String textoText() {
		return texto.replaceAll("\\<.*?\\>", "").replace("&nbsp;", "");
	}
	
	public String corTipo() {
		switch(tipo) {
		case 0:
			return "#36AFFF";
		case 1:
			return "#f6ec09";
		case 2:
			return "#7d09f6";
		case 3:
			return "#18c747";
		case 4:
			return "#e31433";
		case 5:
			return "#70dee7";
		default:
			return null;
	}
	}
	
	public String textoTipo() {
		switch(tipo) {
			case 0:
				return "nota";
			case 1:
				return "pendência";
			case 2:
				return "solução pendência";
			case 3:
				return "conclusão";
			case 4:
				return "reabertura";
			case 5:
				return "arquivamento";
			default:
				return null;
		}
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
	@Lob
	@NotEmpty
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getCriacao() {
		return criacao;
	}
	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}
	@NotNull
	@ManyToOne
	public Voluntario getCriador() {
		return criador;
	}
	public void setCriador(Voluntario criador) {
		this.criador = criador;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	@NotNull
	@ManyToOne
	public Demanda getDemanda() {
		return demanda;
	}
	public void setDemanda(Demanda demanda) {
		this.demanda = demanda;
	}

	
}
