package com.lacosdaalegria.intralacos.model.recurso;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.lacosdaalegria.intralacos.model.usuario.Role;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Diretoria {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@OneToMany
	private Set<Voluntario> diretores;

	@NotBlank
	private String role;

	private Integer status = 1;

	private Integer ordem;
	 
	public void addDiretor(Voluntario diretor) {
		if(diretores == null) {
			diretores = new HashSet<>();
		}
		diretores.add(diretor);
	}
	
	public void removeDiretor(Voluntario diretor) {
		diretores.removeIf(d -> d.getId().equals(diretor.getId()));
	}

}
