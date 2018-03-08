package com.lacosdaalegria.intralacos.model.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lacosdaalegria.intralacos.model.ongs.Polo;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Regiao{

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@NotNull
	private Integer status = 1;

	@JsonIgnore
	@ManyToOne
	private Polo polo;

}
