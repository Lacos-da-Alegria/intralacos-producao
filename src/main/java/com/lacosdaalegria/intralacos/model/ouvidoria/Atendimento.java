package com.lacosdaalegria.intralacos.model.ouvidoria;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Atendimento {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	
	@Column(name="dt_criacao")
	private Date criacao = new Date();

	@Lob
	@NotBlank
	private String ouvidoria;

	@Lob
	private String resposta;
	private Integer status = 0;

	@ManyToOne
	private Categoria categoria;

	@NotNull
	@ManyToOne
	private Grupo grupo;

	@ManyToOne
	@NotNull
	private Voluntario solicitante;

	@ManyToOne
	private Voluntario responsavel;

	
	public String nomeAtendente(){
		return responsavel == null ? "Sem Responsável" : responsavel.getPrimerioNome();
	}
	
	public String mostraResposta(){
		return resposta == null ? "Ouvidoria ainda <b>não</b> foi respondida!" : resposta.replaceAll("[/\r?\n|\r/]", "");
	}
	
	public String mostraOuvidoria(){
		return ouvidoria.replaceAll("[/\r?\n|\r/]", "");
	}
	
}
