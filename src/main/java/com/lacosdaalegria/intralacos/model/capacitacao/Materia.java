package com.lacosdaalegria.intralacos.model.capacitacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Materia {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String titulo;

	@NotBlank
	private String icone;

	@NotBlank
	private String cor;
	private Integer status = 1;
	private String imagem;
}
