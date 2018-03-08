package com.lacosdaalegria.intralacos.model.social;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Depoimento {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	private Date dtCriacao = new Date();

	@Lob
	@NotBlank
	private String conteudo;
	private Integer status = 1;

	@NotNull
	@ManyToOne
	private Voluntario voluntario;
	private String cor;

}
