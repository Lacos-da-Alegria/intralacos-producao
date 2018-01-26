package com.lacosdaalegria.intralacos.model.usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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

@Entity
@Table
@DynamicUpdate
public class Voluntario {
	
	private Long id;
	private String login;
	private String senha;
	private String confirmaSenha;
	private String email;
	private String cpf;
	private String nome;
	private String nomeDoutor;
	private String nascimento;
	private String ddd;
	private String whatsapp;
	private Regiao regiao;
	private String endereco;
	private Hospital preferencia;
	private String sexo;
	private String como_conheceu;
	private Integer status = 1;
	private String profile;
	private boolean querOngs;
	private boolean aceitaTermo;
	private boolean promovido;
	private Set<Role> roles;
	private Date dtCriacao = new Date(); 
	private Voluntario responsavel;
	private String observacao;
	
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
	
	public void addRole(Role role) {
		if(roles == null)
			roles = new HashSet<>();
		roles.add(role);
	} 
	
	public void removeRole(String role) {
		for(Role r : roles) {
			if(r.getRole().equals(role)) {
				roles.remove(r);
				break;
			}
		}
	}
	
	public boolean hasRole(String role) {
		return roles.stream().filter(r -> Objects.equal(r.getRole(), "ROLE_"+role)).findFirst().isPresent();
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
	
	@Column(unique=true)
	@NotBlank(message = "Forneça um login")
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	@JsonIgnore
	@Length(min = 6, message = "Sua senha deve conter no mínimo 6 caracteres")
	@NotBlank(message = "Forneça uma senha")
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	@Transient
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	@Column(unique=true)
	@Email(message = "Forneça um e-mail válido")
	@NotBlank(message = "Por favor forneça um e-mail")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@CPF(message="Forneça um cpf valido")
	@Column(unique=true)
	@NotBlank(message = "Forneça um cpf valido")
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	@NotBlank(message = "Forneça o seu nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeDoutor() {
		return nomeDoutor;
	}

	public void setNomeDoutor(String nomeDoutor) {
		this.nomeDoutor = nomeDoutor;
	}

	@NotBlank(message = "Informe a sua data de aniversário")
	public String getNascimento() {
		return nascimento;
	}
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	@NotBlank(message = "Forneça um DDD")
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	@Column(unique=true)
	@NotBlank(message = "Forneça o seu whatsapp")
	@Length(min = 9, max=9, message = "O seu whatsapp deve conter 9 digitos")
	public String getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}
	@NotNull(message = "Qual é a sua cidade satélite")
	@ManyToOne
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	@NotNull(message = "Diga qual é a preferência de atividade que você quer")
	@ManyToOne
    @JoinColumn(name = "hospital_id")
	public Hospital getPreferencia() {
		return preferencia;
	}
	public void setPreferencia(Hospital preferencia) {
		this.preferencia = preferencia;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public boolean isAceitaTermo() {
		return aceitaTermo;
	}
	public void setAceitaTermo(boolean aceitaTermo) {
		this.aceitaTermo = aceitaTermo;
	}
	
	public boolean isPromovido() {
		return promovido;
	}

	public void setPromovido(boolean promovido) {
		this.promovido = promovido;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getComo_conheceu() {
		return como_conheceu;
	}
	public void setComo_conheceu(String como_conheceu) {
		this.como_conheceu = como_conheceu;
	}
	@NotNull
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProfile() {
		if(profile == null)
			return "/assets/img/ui-sam.jpg";
		else 
			return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public boolean isQuerOngs() {
		return querOngs;
	}
	public void setQuerOngs(boolean querOngs) {
		this.querOngs = querOngs;
	}
	public Date getDtCriacao() {
		return dtCriacao;
	}
	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	@ManyToOne
	public Voluntario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Voluntario responsavel) {
		this.responsavel = responsavel;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
