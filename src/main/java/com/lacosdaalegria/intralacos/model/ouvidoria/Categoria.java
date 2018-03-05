package com.lacosdaalegria.intralacos.model.ouvidoria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Categoria {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;
	private Integer status = 1;

	@ManyToOne
	private Grupo grupo;
	
}
