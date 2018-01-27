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

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Polo{
	
	private Long id;
	private String nome;
	private Set<Regiao> regioes;
	private Set<Voluntario> membros;
	private Integer status = 1;
	
	public void removeMembro(Voluntario membro) {
		for(Voluntario m : membros) {
			if(m.getId().equals(membro.getId()))
				membros.remove(m);
		}
	}
	
	public void removeRegiao(Regiao regiao) {
		regioes.remove(regiao);
	}
	
	/*
	 * ======================================================================================
	 * ============================== Getters and Setters ===================================
	 * ======================================================================================
	 */
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotBlank
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@JsonIgnore
	@OneToMany	
	public Set<Regiao> getRegioes() {
		return regioes;
	}
	public void setRegioes(Set<Regiao> regioes) {
		this.regioes = regioes;
	}
	@NotNull
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonIgnore
	@OneToMany
	public Set<Voluntario> getMembros() {
		return membros;
	}

	public void setMembros(Set<Voluntario> membros) {
		this.membros = membros;
	}
	
}