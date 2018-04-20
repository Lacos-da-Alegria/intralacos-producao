package com.lacosdaalegria.intralacos.service.modules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Objects;
import com.lacosdaalegria.intralacos.model.MaisLacos;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.usuario.ResetToken;
import com.lacosdaalegria.intralacos.model.usuario.Role;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.s3.S3;
import com.lacosdaalegria.intralacos.repository.usuario.ResetTokenRepository;
import com.lacosdaalegria.intralacos.repository.usuario.RoleRepository;
import com.lacosdaalegria.intralacos.repository.usuario.VoluntarioRepository;

@Service
public class VoluntarioService {
	
	@Autowired
	private VoluntarioRepository repository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private S3 s3;
	@Autowired
	private ResetTokenRepository token;
	@Autowired
	private RoleRepository role;
	
	public void registerVoluntario(Voluntario voluntario) {
		
		voluntario.setSenha(bCryptPasswordEncoder.encode(voluntario.getSenha()));
		
		voluntario.addRole(getRole("ROLE_ACEITE"));
		
		repository.save(voluntario);
	}
	
	public void duplicidadeInfo(Voluntario v, BindingResult result) {
		Iterable<Voluntario> voluntarios = repository.
				findByEmailOrLoginOrWhatsappOrCpf(v.getEmail(), v.getLogin(), v.getWhatsapp(), v.getCpf());
		if(voluntarios != null) {
			for(Voluntario vol : voluntarios) {
				v.verificaDuplicidade(vol, result);
			}
		}
	}
	
	public void createRole(String papel) {
		Role role = new Role();
		role.setRole("ROLE_"+papel);
		this.role.save(role);
	}
	
	public Voluntario promoteNovato(Voluntario voluntario){

		removeRole(voluntario, "ROLE_NOVATO");
		addRole(voluntario, "ROLE_VOLUNTARIO");
		
		voluntario.setPromovido(true);
		voluntario.setObservacao("Voluntário promovido - " + new Date());
		return repository.save(voluntario);
	}
	
	public void desativaNovato(Voluntario voluntario){
		voluntario.setStatus(2);
		voluntario.setObservacao("Novato não foi na atividade confirmada!");
		repository.save(voluntario);
	}
	
	
	/*
	 * ======================================================================================
	 * =================================== NOVATO ===========================================
	 * ======================================================================================
	 */
	
	public Integer getPosicao(Voluntario novato) {
		return repository.findPosicaoFila(novato.getDtCriacao(), novato.getPreferencia());
	}
	
	public void updatePreferencia(Voluntario novato, Hospital hospital) {
		novato.setPreferencia(hospital);
		repository.save(novato);
	}
	
	public void reativarConta(Voluntario voluntario) {
		voluntario.setStatus(1);
		repository.save(voluntario);
	}
	
	public void admin(Voluntario voluntario) {
		
		voluntario.addRole(getRole("ROLE_ADMIN"));
		
		repository.save(voluntario);
	}
	
	public void initMaisLacos(MaisLacos maisLacos, Voluntario voluntario) {
		maisLacos.setVoluntarios(repository.findTotalVoluntarios());
		maisLacos.setNovatos(repository.findTotalNovatos());
	}
	
	public Voluntario findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	public void aceitaTermo(Voluntario voluntario) {
		
		addRole(voluntario, "ROLE_NOVATO");
		voluntario.setAceitaTermo(true);
		updateRole("ROLE_NOVATO");
		repository.save(voluntario);
		
	}
	
	public Voluntario addRole(String email, String role) {

		Voluntario voluntario = repository.findByEmail(email);
		
		voluntario.getRoles().add(getRole(role));
		
		return repository.save(voluntario);
	}
	
	public Voluntario addRole(Voluntario voluntario, String role) {
		voluntario.getRoles().add(getRole(role));
		return repository.save(voluntario);
	}
	
	public void removeRole(Voluntario voluntario, String role) {
		voluntario.removeRole(role);
		repository.save(voluntario);
	}
	
	public Voluntario getByLogin(String login) {
		return repository.findByLogin(login);
	}
	
	public void updateUserInfo(Voluntario voluntario, Voluntario update) {
		voluntario.updateInfo(update);
		repository.save(voluntario);
	}
	
	public void updateUserSustentacao(Voluntario voluntario) {
		Voluntario v = repository.findById(voluntario.getId()).get();
		v.updateInfoSustentacao(voluntario);
		repository.save(v);
	}
	
	public void updateProfile(MultipartFile file, Voluntario voluntario) {
		String profile = s3.carregaImagem("pic", voluntario.getId().toString(), file);
		voluntario.setProfile("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-318693850464/" + profile);
		repository.save(voluntario);
	}
	
	private void updateRole(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority(role)); 
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
	
	public Iterable<Voluntario> aniversariantes(){
		return repository.findByNascimentoLikeAndStatus(dia()+"/"+mes()+"%", 1);
	}
	
	public void addResponsavel(Voluntario voluntario, Voluntario responsavel) {
		if(voluntario.getResponsavel() == null) {
			voluntario.setResponsavel(responsavel);
			repository.save(voluntario);
		}
	}
	
	public void removeReponsavel(Voluntario voluntario) {
		voluntario.setResponsavel(null);
		repository.save(voluntario);
	}
	
	public void atualizarObservacao(Voluntario voluntario, Voluntario responsavel, String observacao) {
		if(responsavel.getId().equals(voluntario.getResponsavel().getId())) {
			voluntario.setObservacao(observacao);
			repository.save(voluntario);
		}
	}
	
	public void desativarNovato(Voluntario voluntario, Voluntario responsavel, String observacao) {
		if(responsavel.getId().equals(voluntario.getResponsavel().getId())) {
			voluntario.setObservacao(observacao);
			voluntario.setStatus(2);
			repository.save(voluntario);
		}
	}
	
	public ResetToken createToken(Voluntario voluntario) {
		ResetToken token = new ResetToken();
		token.setVoluntario(voluntario);
		this.token.save(token);
		return token;
	}
	
	public String resetSenha(String token, String senha, String _senha) {
		String mensagem = "";
		ResetToken rToken = this.token.findByTokenAndStatusAndCriacaoGreaterThanEqual(token, 1, vencimentoToken());
		if(rToken != null) {
			mensagem = resetSenha(rToken.getVoluntario(), senha, _senha, rToken);
		} else 
			mensagem = "@O token utilizado para resetar a senha é inválido";
		return mensagem;
	}
	
	public boolean validaSenha(Voluntario voluntario, String senha) {
		return bCryptPasswordEncoder.matches(senha, voluntario.getSenha());
	}
	
	public Voluntario searchSustentacao(String campo, String valor) {
		if(Objects.equal(campo, "email")) {
			return repository.findByEmail(valor);
		} 
		if(Objects.equal(campo, "cpf")) {
			return repository.findByCpf(valor);
		} 
		if(Objects.equal(campo, "whatsapp")) {
			return repository.findByWhatsapp(valor);
		} 
		
		return repository.findByLogin(valor);
	}
	
	private String resetSenha(Voluntario voluntario, String senha, String senha_, ResetToken token) {
		if(Objects.equal(senha, senha_)) {
			voluntario.setSenha(bCryptPasswordEncoder.encode(senha));
			repository.save(voluntario);
			token.setStatus(2);
			this.token.save(token);
			return "Senha atualizada com sucesso!";
		} else {
			return "@As senha fornecidas não coincidem";
		}
	}
	
	public Role getRole(String role) {
		return this.role.findByRole(role);
	}
	
	//Migrar regras de datas para classe Calendario
	private Date vencimentoToken() {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		c.add(Calendar.HOUR, -2);
		return c.getTime();
	}
	
	private String dia() {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		
		String retorno = "";
		
		if(dia < 10) {
			retorno = "0" + dia;
		} else {
			retorno = dia+"";
		}
		
		return retorno;
	}
	
	private String mes() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		int mes = cal.get(Calendar.MONTH) + 1;
		
		String retorno = "";
		
		if(mes < 10) {
			retorno = "0" + mes;
		} else {
			retorno = mes+"";
		}
		
		return retorno;
	}
	
}
