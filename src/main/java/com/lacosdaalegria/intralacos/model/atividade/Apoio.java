package com.lacosdaalegria.intralacos.model.atividade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Apoio {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Hospital hospital;

	@OneToOne
	private Voluntario voluntario;

	private Integer status = 1;

}
