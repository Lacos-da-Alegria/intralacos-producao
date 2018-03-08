package com.lacosdaalegria.intralacos.model.demanda;

import java.util.Date;

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
public class Nota {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@Lob
	@NotBlank
	private String texto;
	private Date criacao = new Date();

	@NotNull
	@ManyToOne
	private Voluntario criador;
	private Integer status = 1;
	private Integer tipo;

	@NotNull
	@ManyToOne
	private Demanda demanda;
	
	/* Tipo de Notas
	 * 0 - Nota
	 * 1 - Pendência
	 * 2 - Resolução de Pendência
	 * 3 - Conclusão
	 * 4 - Reabertura
	 * 5 - Arquivamento
	 */
	
	public String textoText() {
		return texto.replaceAll("\\<.*?\\>", "").replace("&nbsp;", "");
	}
	
	public String corTipo() {
		switch(tipo) {
		case 0:
			return "#36AFFF";
		case 1:
			return "#f6ec09";
		case 2:
			return "#7d09f6";
		case 3:
			return "#18c747";
		case 4:
			return "#e31433";
		case 5:
			return "#70dee7";
		default:
			return null;
	}
	}
	
	public String textoTipo() {
		switch(tipo) {
			case 0:
				return "nota";
			case 1:
				return "pendência";
			case 2:
				return "solução pendência";
			case 3:
				return "conclusão";
			case 4:
				return "reabertura";
			case 5:
				return "arquivamento";
			default:
				return null;
		}
	}
}
