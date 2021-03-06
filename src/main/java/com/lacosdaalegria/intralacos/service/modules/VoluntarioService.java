package com.lacosdaalegria.intralacos.service.modules;

import com.google.common.base.Objects;
import com.lacosdaalegria.intralacos.email.Email;
import com.lacosdaalegria.intralacos.email.EmailService;
import com.lacosdaalegria.intralacos.model.MaisLacos;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.usuario.ControleEntrada;
import com.lacosdaalegria.intralacos.model.usuario.Role;
import com.lacosdaalegria.intralacos.model.usuario.UserToken;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.model.usuario.enuns.RoleEnum;
import com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum;
import com.lacosdaalegria.intralacos.repository.s3.S3;
import com.lacosdaalegria.intralacos.repository.usuario.ControleEntradaRepository;
import com.lacosdaalegria.intralacos.repository.usuario.UserTokenRepository;
import com.lacosdaalegria.intralacos.repository.usuario.RoleRepository;
import com.lacosdaalegria.intralacos.repository.usuario.VoluntarioRepository;
import com.lacosdaalegria.intralacos.service.HospitalService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lacosdaalegria.intralacos.model.usuario.enuns.StatusEnum.*;
import static com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum.ATIVAR_NOVATO;
import static com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum.RESETAR_SENHA;

@Service
@Transactional
@RequiredArgsConstructor
public class VoluntarioService {
	
	private @NonNull VoluntarioRepository repository;
	private @NonNull BCryptPasswordEncoder bCryptPasswordEncoder;
	private @NonNull S3 s3;
	private @NonNull UserTokenRepository token;
	private @NonNull RoleRepository role;
	private @NonNull HospitalService hospitalService;
	private @NonNull ControleEntradaRepository controleEntradaRepository;
	private @NonNull EmailService emailService;

	@Transactional
	public void registerVoluntario(Voluntario voluntario) {
		
		voluntario.setSenha(bCryptPasswordEncoder.encode(voluntario.getSenha()));

		voluntario  = repository.save(voluntario);

		addRole(voluntario, RoleEnum.ACEITE);
	}
	
	
	/* ==========================================================
	 * ================ Bloco de Verificações ===================
	   ==========================================================*/
	
	public void verificaInfo(Voluntario v, BindingResult result) {
		
		verificaDuplicidade(v, result);
		verificarAtualizar(v, result);
		verificaLogin(v, result);
		verificaSenha(v, result);
	}
	
	public void verificaInfoSustentancao(Voluntario v, BindingResult result) {
		verificaDuplicidade(v, result);
		verificaLogin(v, result);
	}
	
	public void verificarAtualizar(Voluntario v, BindingResult result) {
		verificaNascimento(v, result);
		verificaWhats(v, result);
	}
	
	private void verificaDuplicidade(Voluntario v, BindingResult result) {
		
		Iterable<Voluntario> voluntarios = repository.
				findByEmailOrLoginOrWhatsappOrCpf(v.getEmail(), v.getLogin(), v.getWhatsapp(), v.getCpf());
		
		if(voluntarios != null) {
			for(Voluntario vol : voluntarios) {
				v.verificaDuplicidade(vol, result);
			}
		}
		
	}
	
	private void verificaNascimento(Voluntario voluntario, BindingResult result) {
		
		if (!voluntario.getNascimento().matches("\\d{2}/\\d{2}/\\d{4}")) {
			result.rejectValue("nascimento", "erro");
			return;
		}
		
		try {
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			formatter.setLenient(false);
			formatter.parse(voluntario.getNascimento());
			
		} catch(ParseException e) {
			result.rejectValue("nascimento", "erro");
		}
		
	}
	
	private void verificaLogin(Voluntario voluntario, BindingResult result) {
		
		Pattern p = Pattern.compile("[^a-z0-9]");
		Matcher m = p.matcher(voluntario.getLogin());
		boolean b = m.find();
		
		if (b == true || voluntario.getLogin().contains(" ")){
			result.rejectValue("login", "invalido");
		}
		
	}
	
	private void verificaSenha(Voluntario voluntario, BindingResult result) {
		if (!voluntario.getSenha().equals(voluntario.getConfirmaSenha())){
			result.rejectValue("senha", "invalida");
		}
	}
	
	private void verificaWhats(Voluntario voluntario, BindingResult result) {
		
		String regex = "\\d+";

		if(!voluntario.getDdd().matches(regex)) {
			result.rejectValue("ddd", "invalido");
		}
		
		if(!voluntario.getWhatsapp().matches(regex)) {
			result.rejectValue("whatsapp", "invalido");
		}
		
	}
	
	/* ==========================================================
	 * ============== Fim Bloco de Verificações =================
	   ==========================================================*/
	
	public void createRole(String papel) {
		Role role = new Role();
		role.setRole("ROLE_"+papel);
		this.role.save(role);
	}

	@Transactional
	public Voluntario promoteNovato(Voluntario voluntario){

		removeRole(voluntario, RoleEnum.NOVATO);
		addRole(voluntario, RoleEnum.VOLUNTARIO);
		
		voluntario.setPromovido(true);
		voluntario.setObservacao("Voluntário promovido - " + new Date());

		return repository.save(voluntario);
	}

	@Transactional
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

	@Transactional
	public void updatePreferencia(Voluntario novato, Hospital hospital) {
		novato.setPreferencia(hospital);
		repository.save(novato);
	}
	
	public void reativarConta(Voluntario voluntario) {
		voluntario.setStatus(1);
		repository.save(voluntario);
	}
	
	public void admin(Voluntario voluntario) {
		this.addRole(voluntario, RoleEnum.ADMIN);
	}
	
	public void initMaisLacos(MaisLacos maisLacos, Voluntario voluntario) {
		maisLacos.setVoluntarios(repository.findTotalVoluntarios());
		maisLacos.setNovatos(repository.findTotalNovatos());
	}

	
	public Voluntario findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Transactional
	public void aceitaTermo(Voluntario voluntario) {
		
		this.addRole(voluntario, RoleEnum.NOVATO);
		this.removeRole(voluntario, RoleEnum.ACEITE);
		
		voluntario.setAceitaTermo(true);
		
		updateRole(RoleEnum.NOVATO);
		
		repository.save(voluntario);
		
	}

	@Transactional
	public Voluntario addRole(Voluntario voluntario, RoleEnum roleEnum) {

	    Set<Role> roles = treatRoles(voluntario.getRoles(), roleEnum);
	 
        roles.add(getRole(roleEnum));

		voluntario.setRoles(roles);

		return repository.save(voluntario);
	}
	
	private Set<Role> treatRoles(Set<Role> roles, RoleEnum roleEnum){
		
		 if(roles != null) {
		        roles.removeIf(r -> r.getId().equals(roleEnum.getCodigo()));
		 	} else {
		        roles = new HashSet<>();
		 	}
		 
		 return roles;
	}

	@Transactional
    public Voluntario addRole(String email, RoleEnum roleEnum) {

	    Voluntario voluntario = repository.findByEmail(email);

	    return addRole(voluntario, roleEnum);
    }

	@Transactional
    public Voluntario removeRole(Voluntario voluntario, RoleEnum roleEnum) {

        Set<Role> roles = voluntario.getRoles();

        roles.removeIf(r -> r.getId().equals(roleEnum.getCodigo()));

        voluntario.setRoles(roles);

		return repository.save(voluntario);

	}

	@Transactional
	public Voluntario getByLogin(String login) {
		return repository.findByLogin(login);
	}
	
	@Transactional
	public void updateUserInfo(Voluntario voluntario, Voluntario update) {
		voluntario.updateInfo(update);
		repository.save(voluntario);
	}
	
	@Transactional
	public Voluntario updateUserSustentacao(Voluntario voluntario) {
		Voluntario v = repository.findById(voluntario.getId()).get();
		v.updateInfoSustentacao(voluntario);
		return repository.save(v);
	}
	
	@Transactional
	public void updateProfile(MultipartFile file, Voluntario voluntario) {
		String profile = s3.carregaImagem("pic", voluntario.getId().toString(), file);
		voluntario.setProfile("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-318693850464/" + profile);
		repository.save(voluntario);
	}
	
	private void updateRole(RoleEnum roleEnum) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority(roleEnum.getPapel()));
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
	
	public Iterable<Voluntario> aniversariantes(){
		return repository.findByNascimentoLikeAndStatus(dia()+"/"+mes()+"%", 1);
	}

	@Transactional
	public void addResponsavel(Voluntario voluntario, Voluntario responsavel) {
		if(voluntario.getResponsavel() == null) {
			voluntario.setResponsavel(responsavel);
			repository.save(voluntario);
		}
	}


	@Transactional
	public void removeReponsavel(Voluntario voluntario) {
		voluntario.setResponsavel(null);
		repository.save(voluntario);
	}

	@Transactional
	public void atualizarObservacao(Voluntario voluntario, Voluntario responsavel, String observacao) {
		if(responsavel.getId().equals(voluntario.getResponsavel().getId())) {
			voluntario.setObservacao(observacao);
			repository.save(voluntario);
		}
	}

	@Transactional
	public void desativarNovato(Voluntario voluntario, Voluntario responsavel, String observacao) {
		if(responsavel.getId().equals(voluntario.getResponsavel().getId())) {
			voluntario.setObservacao(observacao);
			voluntario.setStatus(2);
			repository.save(voluntario);
		}
	}

	@Transactional
	public UserToken createResetToken(Voluntario voluntario) {
		UserToken token = new UserToken();
		token.setVoluntario(voluntario);
		token.setTokenType(RESETAR_SENHA.obj());
		this.token.save(token);
		return token;
	}
	
	@Transactional
	public void ativarVoluntarios() {
		Iterable<Voluntario> voluntarios = repository.findVoluntarioAtivar();
		
		voluntarios.forEach(v -> v.setStatus(1));
		
		repository.saveAll(voluntarios);
	}
	
	@Transactional
	public void desativarVoluntarios() {
		Iterable<Voluntario> voluntarios = repository.findVoluntarioDesativar();
		
		voluntarios.forEach(v -> v.setStatus(2));
		
		repository.saveAll(voluntarios);
		
	} 
	
	public String resetSenha(String token, String senha, String _senha) {
		String mensagem = "";
		UserToken rToken = getUserTokenExpires(token, RESETAR_SENHA);
		if(rToken != null) {
			mensagem = resetSenha(rToken.getVoluntario(), senha, _senha, rToken);
		} else 
			mensagem = "@O token utilizado para resetar a senha é inválido";
		return mensagem;
	}

	public UserToken getUserTokenExpires(String token, TokenTypeEnum tokenType){
		return this.token.findByTokenAndStatusAndCriacaoGreaterThanEqualAndTokenType(token, ATIVO.obj(), vencimentoToken(), tokenType.obj());
	}

    public UserToken getUserToken(String token, TokenTypeEnum tokenType){
        return this.token.findByTokenAndStatusAndTokenType(token, ATIVO.obj(), tokenType.obj());
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
	
	private String resetSenha(Voluntario voluntario, String senha, String senha_, UserToken token) {
		if(Objects.equal(senha, senha_)) {
			voluntario.setSenha(bCryptPasswordEncoder.encode(senha));
			repository.save(voluntario);
			token.setStatus(INATIVO.obj());
			this.token.save(token);
			return "Senha atualizada com sucesso!";
		} else {
			return "@As senha fornecidas não coincidem";
		}
	}
	
	public Role getRole(RoleEnum roleEnum) {
		return this.role.findById(roleEnum.getCodigo()).orElse(null);
	}


    /*
     * ======================================================================================
     * ====================== Confirmar Participação Novato por Email =======================
     * ======================================================================================
     */
	

    //Rotica para registrar novatos que precisammos enviar o email
    public void gerarTokenConfirmacaoNovatos(){

        desativarInativos();

        registraAptosParaConfirmacao();

    }

    private void desativarInativos(){

        controleEntradaRepository.findByTokenTypeAndStatus(ATIVO.obj()).forEach(c -> tratarControle(c));

    }

    private void tratarControle(ControleEntrada controleEntrada){

        desativaNovato(controleEntrada.getUserToken().getVoluntario());

        controleEntrada.getUserToken().setStatus(INATIVO.obj());
        controleEntrada.setStatus(INATIVO.obj());

        controleEntradaRepository.save(controleEntrada);

    }

    private void registraAptosParaConfirmacao(){

        hospitalService.getAllActive()
                .forEach(h -> repository.novatosAptosConfirmacao(h.getId())
                        .forEach(v -> gerarRegistroControleEntrada(v)));

    }

    private void gerarRegistroControleEntrada(Voluntario voluntario){
        controleEntradaRepository.save(ControleEntrada.build(voluntario));
    }


    //Rotina para envio de email
    public void enviarEmailConfirmacao(){
        controleEntradaRepository.findByTokenTypeAndStatus(ATIVO.obj())
                .forEach(c -> emailService.send(Email.build(c.getUserToken())));
    }

    //Confirmação ou desativação de conta
    public void confirmarContaAtiva(String token, RedirectAttributes redirectAttrs){

        ControleEntrada controleEntrada = controleEntradaRepository.findByToken(token);

        if(controleEntrada == null){
            redirectAttrs.addFlashAttribute("errorMessage", "O token informado é inválido");
        } else {

            controleEntrada.setStatus(CONFIRMADO.obj());
            controleEntrada.getUserToken().setStatus(INATIVO.obj());

            controleEntradaRepository.save(controleEntrada);

            redirectAttrs.addFlashAttribute("successMessage", "Show, agora é só aguardar que entraremos em contato!");

        }

    }

    public void destivarConta(String token, RedirectAttributes redirectAttrs){

        ControleEntrada controleEntrada = controleEntradaRepository.findByToken(token);

        if(controleEntrada == null){
            redirectAttrs.addFlashAttribute("errorMessage", "O token informado é inválido");
        } else {

            controleEntrada.setStatus(SEM_INTERESSE.obj());
            controleEntrada.getUserToken().setStatus(INATIVO.obj());

            controleEntradaRepository.save(controleEntrada);

            desativaNovato(controleEntrada.getUserToken().getVoluntario());

            redirectAttrs.addFlashAttribute("successMessage", "A sua conta foi desativada com sucesso");

        }
    }

	/*
	 * ==================================================================================================================
	 */
	
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
