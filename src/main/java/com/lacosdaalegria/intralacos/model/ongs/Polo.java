package com.lacosdaalegria.intralacos.model.ongs;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Polo{

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@JsonIgnore
	@OneToMany
	private Set<Regiao> regioes;

	@JsonIgnore
	@OneToMany
	private Set<Voluntario> membros;

	@NotNull
	private Integer status = 1;
	
	public void removeMembro(Voluntario membro) {
		membros.removeIf(m -> m.getId().equals(membro.getId()));
	}
	
	public void removeRegiao(Regiao regiao) {
		regioes.removeIf(r -> r.getId().equals(regiao.getId()));
	}

}