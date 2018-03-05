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

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Agenda {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	@Getter @Setter	private Long id;

	@ManyToOne
	@Getter @Setter private Instituicao instituicao;

	@NotNull
	@Getter @Setter private String nome;

	@NotNull
	@Getter @Setter private Integer voluntarios;
	@Getter @Setter private boolean limite;
	@Getter @Setter private Date criacao = new Date();

	@ManyToOne
	@Getter @Setter private Voluntario criador;
	@Getter @Setter private Integer status = 1;

	@NotNull
	@Getter @Setter private Date horario;

	@NotNull
	@Getter @Setter private Integer duracao;
	@Setter private boolean inscricao = false;
	@Setter private boolean chamada = false;

	@Transient
	@Getter @Setter private Integer situacao = 0;

	@ManyToOne
	@Getter @Setter private Semana semana;
	
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
