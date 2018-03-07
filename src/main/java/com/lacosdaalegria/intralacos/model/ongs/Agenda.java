package com.lacosdaalegria.intralacos.model.ongs;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Agenda {

	private Long id;
	private Instituicao instituicao;
	private String nome;
	private Integer voluntarios;
	private boolean limite;
	private Date criacao = new Date();
	private Voluntario criador;
	private Integer status = 1;
	private Date horario;
	private Integer duracao;
	private boolean inscricao = false;
	private boolean chamada = false;
	private Integer situacao = 0;
	private Semana semana;
	
	public boolean ehAgora(int dia, int periodo) {
		return getDia().equals(dia) && getPeriodo().equals(periodo);
	}
			
	public boolean essaSemana(Semana semana) {
		return this.semana.getId().equals(semana.getId());
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
	@ManyToOne
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	@NotNull
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@NotNull
	public Integer getVoluntarios() {
		return voluntarios;
	}
	public void setVoluntarios(Integer voluntarios) {
		this.voluntarios = voluntarios;
	}
	public boolean isLimite() {
		return limite;
	}
	public void setLimite(boolean limite) {
		this.limite = limite;
	}
	public Date getCriacao() {
		return criacao;
	}
	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}
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
	@NotNull
	public Date getHorario() {
		return horario;
	}
	public void setHorario(Date horario) {
		this.horario = horario;
	}
	@NotNull
	public Integer getDuracao() {
		return duracao;
	}
	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
	@Transient
	public Integer getDia() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(horario);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	@Transient
	public Integer getPeriodo() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(horario);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour >= 4 && hour < 12) {
			return 1;
		} else {
			if(hour >= 12 && hour <= 18) {
				return 2;
			} else {
				return 3;
			}
		}
	}
	public boolean isInscricao() {
		return inscricao;
	}
	public void setInscricao(boolean inscricao) {
		this.inscricao = inscricao;
	}
	public boolean isChamada() {
		return chamada;
	}
	public void setChamada(boolean chamada) {
		this.chamada = chamada;
	}
	
	@ManyToOne
	public Semana getSemana() {
		return semana;
	}

	public void setSemana(Semana semana) {
		this.semana = semana;
	}
	
	@Transient
	public Integer getSituacao() {
		return situacao;
	}
	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

}
