package com.lacosdaalegria.intralacos.model.ouvidoria;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.lacosdaalegria.intralacos.model.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Atendimento {
	
	private Long id;
	private Date dt_criacao = new Date();
	private String ouvidoria;
	private String resposta;
	private Integer status = 0;
	private Categoria categoria;
	private Grupo grupo;
	private Voluntario solicitante;
	private Voluntario responsavel;

	
	public String nomeAtendente(){
		return responsavel == null ? "Sem Responsável" : responsavel.getPrimerioNome();
	}
	
	public String mostraResposta(){
		return resposta == null ? "Ouvidoria ainda <b>não</b> foi respondida!" : resposta.replaceAll("[/\r?\n|\r/]", "");
	}
	
	public String mostraOuvidoria(){
		return ouvidoria.replaceAll("[/\r?\n|\r/]", "");
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
	public Date getDt_criacao() {
		return dt_criacao;
	}
	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}
	@NotEmpty
	public String getOuvidoria() {
		return ouvidoria;
	}
	public void setOuvidoria(String ouvidoria) {
		this.ouvidoria = ouvidoria;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	@NotNull
	@ManyToOne
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	@ManyToOne
	@NotNull
	public Voluntario getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Voluntario solicitante) {
		this.solicitante = solicitante;
	}
	@ManyToOne
	public Voluntario getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Voluntario responsavel) {
		this.responsavel = responsavel;
	}
	
	

}
