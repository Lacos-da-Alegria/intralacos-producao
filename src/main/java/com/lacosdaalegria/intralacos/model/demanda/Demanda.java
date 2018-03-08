package com.lacosdaalegria.intralacos.model.demanda;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Demanda {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	private Date criacao = new Date();

	@NotNull
	@ManyToOne
	private Voluntario criador;

	@ManyToOne
	private Voluntario responsavel;

	@NotNull
	private Integer prazo;
	private Integer status = 0;

	@NotBlank
	private String titulo;

	@NotBlank
	@Lob
	private String descricao;

	@NotNull
	@ManyToOne
	private Equipe equipe;

	@OneToMany
	private List<Nota> notas;
	
	public Long diasVencimento() {
		Long dias = TimeUnit.DAYS.convert(criacao.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);
		Long vencimento = prazo - dias;
		if(vencimento < 0)
			vencimento = 0l;
		return vencimento;
	}
	
	public String criticidade(){
		
		if(diasVencimento() >= 10){
			return "success";
		} else {
			if(diasVencimento() >= 5){
				return "warning";
			} else {
				return "danger";
			}
		}
	}

	public String descricaoText() {
		return descricao.replaceAll("\\<.*?\\>", "").replace("&nbsp;", "");
	}
}
