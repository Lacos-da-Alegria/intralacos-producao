package com.lacosdaalegria.intralacos.model.usuario;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class ResetToken {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	private Voluntario voluntario;
	private String token = UUID.randomUUID().toString();
	private Integer status = 1;
	private Date criacao = new Date();

}
