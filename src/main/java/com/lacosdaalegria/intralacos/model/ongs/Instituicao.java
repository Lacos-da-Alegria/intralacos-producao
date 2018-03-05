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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Regiao;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Instituicao {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;
	private Integer status = 1;

	@NotBlank
	private String telefone;

	@NotBlank
	private String email;

	@NotBlank
	private String responsavel;
	private String contatoResponsavel;
	private String emailResponsavel;

	@Lob
	private String descricao;

	@Lob
	private String atividades;

	@NotBlank
	private String endereco;

	@ManyToMany
	private Set<Tag> tags;
	private boolean fomos;

	@Lob
	private String pontosCriticos;

	@ManyToOne
	private Regiao regiao;
	private String imagem;

	@NotNull
	@ManyToOne
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
}
