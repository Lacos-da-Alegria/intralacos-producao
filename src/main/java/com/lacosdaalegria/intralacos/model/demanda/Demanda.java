package com.lacosdaalegria.intralacos.model.demanda;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Demanda {
	
	private Long id;
	private Date criacao = new Date();
	private Voluntario criador;
	private Voluntario responsavel;
	private Integer prazo;
	private Integer status = 0;
	private String titulo;
	private String descricao;
	private Equipe equipe;
	private List<Nota> notas;
	
	public Long diasVencimento() {
		Long dias = TimeUnit.DAYS.convert(criacao.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
		Long vencimento = prazo - dias;
		if(vencimento < 0)
			vencimento = 0l;
		return vencimento;
	}
	
	public String criticidade(){
		
		if(diasVencimento() >= 10){
			return "success";
		} else {
			if(diasVencimento() >= 5){
				return "warning";
			} else {
				return "danger";
			}
		}
	}

	public String descricaoText() {
		return descricao.replaceAll("\\<.*?\\>", "").replace("&nbsp;", "");
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
	@ManyToOne
	public Voluntario getCriador() {
		return criador;
	}
	public void setCriador(Voluntario criador) {
		this.criador = criador;
	}
	@ManyToOne
	public Voluntario getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Voluntario responsavel) {
		this.responsavel = responsavel;
	}
	@NotNull
	public Integer getPrazo() {
		return prazo;
	}
	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@NotBlank
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	@NotBlank
	@Lob
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@NotNull
	@ManyToOne
	public Equipe getEquipe() {
		return equipe;
	}
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	@OneToMany
	public List<Nota> getNotas() {
		return notas;
	}
	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

}
