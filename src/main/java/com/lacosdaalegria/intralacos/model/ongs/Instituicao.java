package com.lacosdaalegria.intralacos.model.ongs;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.lacosdaalegria.intralacos.model.Regiao;

@Entity
@Table
@DynamicUpdate
public class Instituicao {
	
	private Long id;
	private String nome;
	private Integer status = 1;
	private String telefone;
	private String email;
	private String responsavel;
	private String contatoResponsavel;
	private String emailResponsavel;
	private String descricao;
	private String atividades;
	private String endereco;
	private Set<Tag> tags;
	private boolean fomos;
	private String pontosCriticos;
	private Regiao regiao;
	private String imagem;
	private Polo polo;
	
	public Set<Tag> caracteristicas(){
		return tags.stream().filter(tag -> tag.getTipo().equals(1)).
				collect(Collectors.toSet());
	}
	
	public Set<Tag> atividades(){
		return tags.stream().filter(tag -> tag.getTipo().equals(2)).
				collect(Collectors.toSet());
	}
	
	public Set<Tag> pontosCriticos(){
		return tags.stream().filter(tag -> tag.getTipo().equals(3)).
				collect(Collectors.toSet());
	}
	
	public String imagem() {
		if(imagem == null)
			return "/assets/img/acao.jpg";
		else
			return imagem;
	}
	
	public String jaFomos() {
		return fomos ? "Sim" : "Não";
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
	@NotEmpty
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@NotEmpty
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@NotEmpty
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	@NotEmpty
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@NotEmpty
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getContatoResponsavel() {
		return contatoResponsavel;
	}
	public void setContatoResponsavel(String contatoResponsavel) {
		this.contatoResponsavel = contatoResponsavel;
	}
	public String getEmailResponsavel() {
		return emailResponsavel;
	}
	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}
	@Lob
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Lob
	public String getAtividades() {
		return atividades;
	}
	public void setAtividades(String atividades) {
		this.atividades = atividades;
	}
	@ManyToMany
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public boolean isFomos() {
		return fomos;
	}
	public void setFomos(boolean fomos) {
		this.fomos = fomos;
	}
	@Lob
	public String getPontosCriticos() {
		return pontosCriticos;
	}
	public void setPontosCriticos(String pontosCriticos) {
		this.pontosCriticos = pontosCriticos;
	}
	@ManyToOne
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	@NotNull
	@ManyToOne
	public Polo getPolo() {
		return polo;
	}
	public void setPolo(Polo polo) {
		this.polo = polo;
	}
	
}
