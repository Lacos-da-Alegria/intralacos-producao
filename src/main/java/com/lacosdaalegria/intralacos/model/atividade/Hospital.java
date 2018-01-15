package com.lacosdaalegria.intralacos.model.atividade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
public class Hospital {
	
	private Long id;
	private String nome;
	private String tag;
	private Integer status = 0;
	private String descricao;
	private String endereco;
	private String localPreparo;
	private String horario;
	private Integer dia;
	private Integer periodo;
	private Integer limiteVoluntario;
	private Integer limiteApoio;
	private Integer limiteNovato;
	private String imagem;
	private Boolean inscricao = true;
	private Boolean chamada = false;
	private Semana semana;
	
	private Integer situacao = 0;
	
	public String imageHospital() {
		if(imagem == null)
			return "/assets/img/padraoHosp.jpg";
		else
			return imagem;
	}	
	
	public boolean ehAgora(int dia, int periodo) {
		return getDia().equals(dia) && getPeriodo().equals(periodo);
	}
	
	public boolean essaSemana(Semana semana) {
		return this.semana.getId().equals(semana.getId());
	}
	
	public void updateCoordenador(Hospital hospital) {
		updateDefault(hospital);
	}
	
	public void updateDiretor(Hospital hospital) {
		nome = hospital.getNome();
		dia = hospital.getDia();
		periodo = hospital.getPeriodo();
		updateDefault(hospital);
	}
	
	private void updateDefault(Hospital hospital) {
		endereco = hospital.getEndereco();
		localPreparo = hospital.getLocalPreparo();
		horario = hospital.getHorario();
		limiteVoluntario = hospital.getLimiteVoluntario();
		limiteNovato = hospital.getLimiteNovato();
		limiteApoio = hospital.getLimiteApoio();
		descricao = hospital.getDescricao();
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
	@NotBlank
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	@NotNull
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Lob
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getLocalPreparo() {
		return localPreparo;
	}
	public void setLocalPreparo(String localPreparo) {
		this.localPreparo = localPreparo;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	@NotNull
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	@NotNull
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	@Max(22)
	@NotNull
	public Integer getLimiteVoluntario() {
		return limiteVoluntario;
	}
	public void setLimiteVoluntario(Integer limiteVoluntario) {
		this.limiteVoluntario = limiteVoluntario;
	}
	@Max(8)
	@NotNull
	public Integer getLimiteApoio() {
		return limiteApoio;
	}
	public void setLimiteApoio(Integer limiteApoio) {
		this.limiteApoio = limiteApoio;
	}
	@Max(8)
	@NotNull
	public Integer getLimiteNovato() {
		return limiteNovato;
	}
	public void setLimiteNovato(Integer limiteNovato) {
		this.limiteNovato = limiteNovato;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public Boolean getInscricao() {
		return inscricao;
	}

	public void setInscricao(Boolean inscricao) {
		this.inscricao = inscricao;
	}

	public Boolean getChamada() {
		return chamada;
	}

	public void setChamada(Boolean chamada) {
		this.chamada = chamada;
	}
	
	@ManyToOne
	public Semana getSemana() {
		return semana;
	}

	public void setSemana(Semana semana) {
		this.semana = semana;
	}

	@Transient
	public Integer getSituacao() {
		return situacao;
	}
	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
	

}
