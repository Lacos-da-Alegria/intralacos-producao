package com.lacosdaalegria.intralacos.model.sustentacao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Promocao {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotNull
	@OneToOne
	private Voluntario novato;

	@NotNull
	@ManyToOne
	private Voluntario analista;

	@Transient
	private Coordenador coordenador_;

	@NotNull
	@ManyToOne
	private Voluntario coordenador;

	@NotNull
	@ManyToOne
	private Hospital primeiraAtividade;
	
	private Date dtCriacao = new Date(); 

}
