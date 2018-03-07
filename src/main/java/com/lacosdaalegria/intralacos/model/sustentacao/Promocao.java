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

import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
public class Promocao {
	
	private Long id;
	private Voluntario novato;
	private Voluntario analista;
	private Coordenador coordenador_;
	private Voluntario coordenador;
	private Hospital primeiraAtividade;
	private Date dtCriacao = new Date(); 
	
	/*
	 * ======================================================================================
	 * ============================== Getters and Setters ===================================
	 * ======================================================================================
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotNull
	@OneToOne
	public Voluntario getNovato() {
		return novato;
	}
	public void setNovato(Voluntario novato) {
		this.novato = novato;
	}
	@NotNull
	@ManyToOne
	public Voluntario getAnalista() {
		return analista;
	}
	public void setAnalista(Voluntario analista) {
		this.analista = analista;
	}
	@NotNull
	@ManyToOne
	public Voluntario getCoordenador() {
		return coordenador;
	}
	public void setCoordenador(Voluntario coordenador) {
		this.coordenador = coordenador;
	}
	@Transient
	public Coordenador getCoordenador_() {
		return coordenador_;
	}
	public void setCoordenador_(Coordenador coordenador_) {
		this.coordenador_ = coordenador_;
	}
	@NotNull
	@ManyToOne
	public Hospital getPrimeiraAtividade() {
		return primeiraAtividade;
	}
	public void setPrimeiraAtividade(Hospital primeiraAtividade) {
		this.primeiraAtividade = primeiraAtividade;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

}
