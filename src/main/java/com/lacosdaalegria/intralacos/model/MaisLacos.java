package com.lacosdaalegria.intralacos.model;

import org.springframework.stereotype.Component;

@Component
public class MaisLacos {

	private Integer voluntarios;
	private Integer novatos;
	private Integer hospitais = 0;
	private Integer ongs = 0;
	private Integer horas;
	
	public Integer getVoluntarios() {
		return voluntarios;
	}
	public void setVoluntarios(Integer voluntarios) {
		this.voluntarios = voluntarios;
	}
	public Integer getNovatos() {
		return novatos;
	}
	public void setNovatos(Integer novatos) {
		this.novatos = novatos;
	}
	public Integer getHospitais() {
		return hospitais;
	}
	public void setHospitais(Integer hospitais) {
		this.hospitais = hospitais;
	}
	public Integer getOngs() {
		return ongs;
	}
	public void setOngs(Integer ongs) {
		this.ongs = ongs;
	}
	public Integer getHoras() {
		return horas;
	}
	public void setHoras(Integer horas) {
		this.horas = horas;
	}
	
}
