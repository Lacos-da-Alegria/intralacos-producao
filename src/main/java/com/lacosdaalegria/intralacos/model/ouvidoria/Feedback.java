package com.lacosdaalegria.intralacos.model.ouvidoria;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Feedback {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@Lob
	@NotBlank
	private String texto;
	private Integer status = 1;
	private Date criacao = new Date();

}
