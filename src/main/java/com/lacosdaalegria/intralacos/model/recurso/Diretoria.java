package com.lacosdaalegria.intralacos.model.recurso;

import java.util.HashSet;
import java.util.Set;

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
public class Diretoria {

	private Long id;
	private String nome;
	private Set<Voluntario> diretores;
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
		for(Voluntario d : diretores){
			if(d.getId().equals(diretor.getId())){
				diretores.remove(d);
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
	@OneToMany
	public Set<Voluntario> getDiretores() {
		return diretores;
	}
	public void setDiretores(Set<Voluntario> diretores) {
		this.diretores = diretores;
	}
	@NotBlank
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	
}
