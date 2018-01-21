package com.lacosdaalegria.intralacos.model.atividade;


import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;

@Entity
@Table
@DynamicUpdate
public class Registro {
	
	private Long id;
	private Voluntario voluntario;
	private Hospital hospital;
	private Agenda agenda;
	private Integer tipo = 0;
	private Integer status = 0;
	private Integer posicao;
	private Date criacao = new Date();
	private Semana semana;
	
	public String corChamada() {
		if(status == 0) 
			return "color: #428bca";
		if(status == 1) 
			return "color: #14c10e";
		else
			return "color: #f22e2e";
	}
	
	public String iconeChamada() {
		if(status == 0) 
			return "fa fa-question";
		if(status == 1) 
			return "fa fa-thumbs-o-up";
		else
			return "fa fa-thumbs-o-down";
	}
	
	public boolean chamadaAberta() {
		if(hospital != null) {
			return hospital.getChamada();
		} else {
			return agenda.isChamada();
		}
	}
	
	public boolean inscricaoAberta() {
		if(hospital != null) {
			return hospital.getInscricao();
		} else {
			return agenda.isInscricao();
		}
	}
	
	/*
	 * Tabela de Tipos
	 * 0 - Normal
	 * 1 - Novato
	 * 2 - Apoio
	 * 3 - Especial (Diretor pertinente e Coordenador pertinente)
	 */
	@Transient
	public boolean isNormal() {
		return tipo.equals(0);
	}
	
	@Transient
	public boolean isNovato() {
		return tipo.equals(1);
	}
	
	@Transient
	public boolean isEquipe() {
		return tipo.equals(2);
	}
	
	@Transient
	public boolean isEspecial() {
		return tipo.equals(3);
	}
	
	public void initRegistro(Hospital hospital) {
		this.hospital = hospital;
	}
	
	public void initRegistro(Agenda agenda) {
		this.agenda = agenda;
		initTipoInstituicao();
	}
	
	private void initTipoInstituicao(){
		if(voluntario.isPromovido()) {
			if(voluntario.hasRole("ONGS")) {
				tipo = 3;
			} else {
				if(voluntario.hasRole("DONGS")) {
					tipo = 3;
				} else {
					if(voluntario.hasRole("DEXEC") || voluntario.hasRole("DCOM")) {
						tipo = 3;
					} else {
						tipo = 0;
					}
				}
			}
		} else
			tipo = 1;
	}
	
	public void initPosicao(boolean faltante) {
		if(rodadaRandomica() && !faltante)
			posicao = new Random().nextInt((10000 - 1) + 1) + 1;
		else 
			posicao = 10001;
	}
	
	public boolean rodadaRandomica(){
		return dia() >=2 && dia() <= 3;
	}
	
	private int dia(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(criacao);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public boolean ehMeu(Voluntario voluntario) {
		return this.voluntario.getId().equals(voluntario.getId());
	}
	
	public boolean mesmoHorario(Hospital hospital) {
		return mesmoHorario(hospital.getPeriodo(), hospital.getDia());
	}
	
	public boolean mesmoHorario(Agenda agenda) {
		return mesmoHorario(agenda.getPeriodo(), agenda.getDia());
	}
	
	private boolean mesmoHorario(Integer periodo, Integer dia) {
		if(hospital == null) {
			return agenda.getDia().equals(dia) && agenda.getPeriodo().equals(periodo);
		} else {
			return hospital.getDia().equals(dia) && hospital.getPeriodo().equals(periodo);
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
	@ManyToOne
	public Voluntario getVoluntario() {
		return voluntario;
	}
	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}
	@ManyToOne
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	@ManyToOne
	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	@NotNull
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	@NotNull
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPosicao() {
		return posicao;
	}
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	public Date getCriacao() {
		return criacao;
	}
	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}
	@NotNull
	@ManyToOne
	public Semana getSemana() {
		return semana;
	}
	public void setSemana(Semana semana) {
		this.semana = semana;
	}
	
}
