package com.lacosdaalegria.intralacos.model.recurso;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Equipe {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@ManyToOne
	private Diretoria diretoria;

	@OneToOne
	private Voluntario lider;
	private Integer numeroMembros;

	@OneToMany
	private Set<Voluntario> membros;

	@NotBlank
	private String objetivo;

	@Lob
	private String descricao;
	
	public void updateEquipe(Equipe equipe) {
		numeroMembros = equipe.numeroMembros;
		objetivo = equipe.objetivo;
		descricao = equipe.descricao;
	}
	
	public void addMembro(Voluntario membro) {
		if(membros == null) {
			membros = new HashSet<>();
		}
		membros.add(membro);
	}
	
	public void removeMembro(Voluntario membro) {
		membros.removeIf(m -> m.getId().equals(membro.getId()));
	}

}
