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

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Equipe {
	
	private Long id;
	private String nome;
	private Diretoria diretoria;
	private Voluntario lider;
	private Integer numeroMembros;
	private Set<Voluntario> membros;
	private String objetivo;
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
		for(Voluntario d : membros){
			if(d.getId().equals(membro.getId())){
				membros.remove(d);
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
	@ManyToOne
	public Diretoria getDiretoria() {
		return diretoria;
	}
	public void setDiretoria(Diretoria diretoria) {
		this.diretoria = diretoria;
	}
	@OneToOne
	public Voluntario getLider() {
		return lider;
	}
	public void setLider(Voluntario lider) {
		this.lider = lider;
	}
	public Integer getNumeroMembros() {
		return numeroMembros;
	}
	public void setNumeroMembros(Integer numeroMembros) {
		this.numeroMembros = numeroMembros;
	}
	@OneToMany
	public Set<Voluntario> getMembros() {
		return membros;
	}
	public void setMembros(Set<Voluntario> membros) {
		this.membros = membros;
	}
	@NotBlank
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	@Lob
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
