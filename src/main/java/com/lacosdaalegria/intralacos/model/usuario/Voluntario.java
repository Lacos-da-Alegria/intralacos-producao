package com.lacosdaalegria.intralacos.model.usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;

import br.com.caelum.stella.bean.validation.CPF;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@DynamicUpdate
public class Voluntario {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	@Getter @Setter	private Long id;

	@Column(unique=true)
	@NotBlank(message = "Forneça um login")
	@Getter @Setter	private String login;

	@JsonIgnore
	@Length(min = 6, message = "Sua senha deve conter no mínimo 6 caracteres")
	@NotBlank(message = "Forneça uma senha")
	@Getter @Setter	private String senha;

	@Transient
	@Getter @Setter	private String confirmaSenha;

	@Column(unique=true)
	@Email(message = "Forneça um e-mail válido")
	@NotBlank(message = "Por favor forneça um e-mail")
	@Getter @Setter	private String email;

	@CPF(message="Forneça um cpf valido")
	@Column(unique=true)
	@NotBlank(message = "Forneça um cpf valido")
	@Getter @Setter	private String cpf;

	@NotBlank(message = "Forneça o seu nome")
	@Getter @Setter	private String nome;
	@Getter @Setter	private String nomeDoutor;

	@NotBlank(message = "Informe a sua data de aniversário")
	@Getter @Setter	private String nascimento;

	@NotBlank(message = "Forneça um DDD")
	@Getter @Setter	private String ddd;

	@Column(unique=true)
	@NotBlank(message = "Forneça o seu whatsapp")
	@Length(min = 9, max=9, message = "O seu whatsapp deve conter 9 digitos")
	@Getter @Setter	private String whatsapp;

	@NotNull(message = "Qual é a sua cidade satélite")
	@ManyToOne
	@Getter @Setter	private Regiao regiao;
	@Getter @Setter	private String endereco;

	@NotNull(message = "Diga qual é a preferência de atividade que você quer")
	@ManyToOne
	@JoinColumn(name = "hospital_id")
	@Getter @Setter	private Hospital preferencia;
	@Getter @Setter	private String sexo;
	@Getter @Setter	private String como_conheceu;

	@NotNull
	@Getter @Setter	private Integer status = 1;
	@Setter	private String profile;
	@Setter	private boolean querOngs;
	@Setter	private boolean aceitaTermo;
	@Setter	private boolean promovido;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Getter @Setter	private Set<Role> roles;
	@Getter @Setter	private Date dtCriacao = new Date();

	@ManyToOne
	@Getter @Setter	private Voluntario responsavel;
	@Getter @Setter	private String observacao;
	
	public String profilePic() {
		if(profile == null)
			return "/assets/img/ui-sam.jpg";
		else 
			return profile;
	}
	
	public void updateInfo(Voluntario v) {
		nome = v.getNome();
		nomeDoutor = v.getNomeDoutor();
		nascimento = v.getNascimento();
		ddd = v.getDdd();
		whatsapp = v.getWhatsapp();
		regiao = v.getRegiao();
		endereco = v.getEndereco();
	}
	
	public String responsavel() {
		if(responsavel == null)
			return "Sem Responsavel";
		else
			return responsavel.getPrimerioNome();
	}
	
	@Transient
	public String getPrimerioNome() {
		if(nome.indexOf(" ") == -1)
			return nome;
		return nome.substring(0, nome.indexOf(" "));
	}
	
	@Transient
	public int getIdade(){
		
		LocalDate now = LocalDate.now();
			
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate aniversario = LocalDate.parse(nascimento, formato);
	
		return Period.between(aniversario, now).getYears();
	}
	
	@Transient
	public boolean isAniversariante(){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(nascimento);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			return cal.get(Calendar.WEEK_OF_YEAR) == numeroSemana() || 
						cal.get(Calendar.WEEK_OF_YEAR) == numeroSemana()+1;
		} catch(ParseException e) {
			return false;
		}
	}

	private int numeroSemana(){	
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	public void verificaDuplicidade(Voluntario v, BindingResult result) {
		if(id == null || !id.equals(v.getId())) {
			if(Objects.equal(v.getEmail(), email)) {
				result.rejectValue("email", "erro");
			}
			if(Objects.equal(v.getWhatsapp(), whatsapp)) {
				result.rejectValue("whatsapp", "erro");
			}
			if(Objects.equal(v.getCpf(), cpf)) {
				result.rejectValue("cpf", "erro");
			}
			if(Objects.equal(v.getLogin(), login)) {
				result.rejectValue("login", "erro");
			}
		}
	}
	
	public void updateInfoSustentacao(Voluntario voluntario) {
		login = voluntario.getLogin();
		email = voluntario.getEmail();
		cpf = voluntario.getCpf();
	}

	public boolean hasRole(String role) {
		return roles.stream().filter(r -> Objects.equal(r.getRole(), "ROLE_"+role)).findFirst().isPresent();
	}
	
	/*
	 * ======================================================================================
	 * ============================== Getters and Setters ===================================
	 * ======================================================================================
	 */

	public boolean isPromovido() {
		return promovido;
	}

	public String getProfile() {
		if(profile == null)
			return "/assets/img/ui-sam.jpg";
		else 
			return profile;
	}

}
