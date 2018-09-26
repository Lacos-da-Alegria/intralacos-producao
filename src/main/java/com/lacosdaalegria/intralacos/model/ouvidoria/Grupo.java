package com.lacosdaalegria.intralacos.model.ouvidoria;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Grupo {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@OneToMany(mappedBy="grupo", cascade=CascadeType.ALL)
	private Set<Categoria> categorias;

	@OneToMany
	private Set<Voluntario> atendentes;
	private Integer status = 1;
	
	public void removeAtendete(Voluntario voluntario) {
		atendentes.removeIf(a -> a.getId().equals(voluntario.getId()));
	}

}
