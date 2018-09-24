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

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicUpdate
@Getter @Setter
public class Hospital {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	private String tag;

	@NotNull
	private Integer status = 0;

	@Lob
	private String descricao;
	private String endereco;
	private String localPreparo;
	private String horario;

	@NotNull
	private Integer dia;

	@NotNull
	private Integer periodo;

	@Max(24)
	@NotNull
	private Integer limiteVoluntario;

	@Max(9)
	@NotNull
	private Integer limiteApoio;

	@Max(8)
	@NotNull
	private Integer limiteNovato;
	private String imagem;
	private Boolean inscricao = false;
	private Boolean chamada = false;
	private Boolean novatos = true;
	
	@ManyToOne
	private Semana semana;

	@Transient
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

}
