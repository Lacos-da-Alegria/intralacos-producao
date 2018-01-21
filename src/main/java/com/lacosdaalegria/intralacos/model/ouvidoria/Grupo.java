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

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Grupo {

	private Long id;
	private String nome;
	private Set<Categoria> categorias;
	private Set<Voluntario> atendentes;
	private Integer status = 1;
	
	public void removeAtendete(Voluntario voluntario) {
		for(Voluntario v : atendentes) {
			if(v.getId().equals(voluntario.getId())) {
				atendentes.remove(v);
				break;
			}
		}
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
	
	@OneToMany(mappedBy="grupo", cascade=CascadeType.ALL)
	public Set<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	@OneToMany
	public Set<Voluntario> getAtendentes() {
		return atendentes;
	}
	public void setAtendentes(Set<Voluntario> atendentes) {
		this.atendentes = atendentes;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
