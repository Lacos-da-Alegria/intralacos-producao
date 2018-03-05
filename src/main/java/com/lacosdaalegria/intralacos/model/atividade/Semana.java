package com.lacosdaalegria.intralacos.model.atividade;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Semana {
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	private Date criacao = new Date();

	@NotNull
	private Integer semana;

	@NotNull
	private Integer ano;
	private Integer status = 1;
	
	public Semana() {
		
		super();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(criacao);
		
		semana = cal.get(Calendar.WEEK_OF_YEAR);
		ano = cal.get(Calendar.YEAR);
		
	}
}
