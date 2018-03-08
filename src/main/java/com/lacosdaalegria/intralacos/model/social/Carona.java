package com.lacosdaalegria.intralacos.model.social;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Carona {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	private Date criacao = new Date();

	@NotNull
	@ManyToOne
	private Voluntario criador;

	@Lob
	@NotBlank
	private String rota;

	@OneToMany
	private Set<Voluntario> viajantes;

	@NotNull
	@Min(1)
	private Integer vagas;
	private Integer status = 1;

	@ManyToOne
	private Hospital hospital;

	@ManyToOne
	private Agenda agenda;
	
	public boolean ehCriador(Voluntario voluntario) {
		return criador.getId().equals(voluntario.getId());
	}
}
