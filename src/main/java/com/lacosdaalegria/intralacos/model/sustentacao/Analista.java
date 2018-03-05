package com.lacosdaalegria.intralacos.model.sustentacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter
@Setter
public class Analista {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@OneToOne
	private Voluntario voluntario;

}
