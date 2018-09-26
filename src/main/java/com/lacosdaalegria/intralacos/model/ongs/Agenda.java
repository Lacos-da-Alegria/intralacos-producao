package com.lacosdaalegria.intralacos.model.ongs;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Agenda {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Instituicao instituicao;

	@NotNull
	private String nome;

	@NotNull
	private Integer voluntarios;
	private boolean limite;
	private Date criacao = new Date();

	@ManyToOne
	private Voluntario criador;
	private Integer status = 1;
	
	@Lob
	private String informacoes;
	
	@NotBlank
	private String grupoWhats;

	@NotNull
	private Date horario;

	@NotNull
	private Integer duracao;
	private boolean inscricao = false;
	private boolean chamada = false;
	
	private Integer novatos;

	@Transient
	private Integer situacao = 0;

	@ManyToOne
	private Semana semana;
	
	public boolean ehAgora(int dia, int periodo) {
		return getDia().equals(dia) && getPeriodo().equals(periodo);
	}
			
	public boolean essaSemana(Semana semana) {
		return this.semana.getId().equals(semana.getId());
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
	public boolean isChamada() {
		return chamada;
	}
}
