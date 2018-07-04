package com.lacosdaalegria.intralacos.session;

import java.util.HashSet;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.MaisLacos;
import com.lacosdaalegria.intralacos.model.demanda.Demanda;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.DemandaService;
import com.lacosdaalegria.intralacos.service.modules.OuvidoriaService;
import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;

@Component
@RequiredArgsConstructor
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo {

	private @NonNull VoluntarioService vService;
	private @NonNull OuvidoriaService ouvidoria;
	private @NonNull AtividadeService atividade;
	private @NonNull DemandaService demanda;
	
	private Voluntario voluntario;
	private Integer posicao;
	private Integer atendimentos;
	private MaisLacos maisLacos;
	private boolean faltante;
	private Iterable<Demanda> demandas = new HashSet<>();
	
	public Integer getPosicao() {
		if(posicao == null) {
			posicao = vService.getPosicao(getVoluntario());
		} 
		return posicao;
	}
	
	public void reduzAtendimento() {
		if(atendimentos!=null)
			atendimentos = atendimentos - 1;
	}
	
	public MaisLacos getMaisLacos() {
		if(maisLacos == null) {
			maisLacos = new MaisLacos();
			vService.initMaisLacos(maisLacos, getVoluntario());
			atividade.initMaisLacos(maisLacos, getVoluntario());
		}
		return maisLacos;
	}
	
	public Integer getRespostas() {
		if(atendimentos == null) {
			atendimentos = ouvidoria.minhasRespostas(getVoluntario());
		}
		return atendimentos;
	}
	
	public void resetPosicao() {
		posicao = null;
	}
	
	public void initSession() {
		setVoluntario();
		initFaltante();
		initDemandas();
	}

	public Voluntario getVoluntario() {
		if(voluntario == null)
			setVoluntario();
		return voluntario;
	}

	public void setVoluntario(Voluntario voluntario) {
		this.voluntario = voluntario;
	}
	
	public void setVoluntario() {
		voluntario = vService.getByLogin(getAuthentication().getName());
	}
	
	//Fazer classe com metodos estaticos de segurança
	public boolean hasRole(String role) {
		for(GrantedAuthority  r : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(r.getAuthority().equals(role))
				return true;
		}
		
		return false;
	}
	
	public String roleText() {
		if(hasRole("ROLE_NOVATO"))
			return "Área do Novato";
		else
			return "Área do Voluntário";
	}
	
	public String hrefText() {
		if(hasRole("ROLE_NOVATO"))
			return "/novato/home";
		else
			return "/voluntario/home";
	}
	
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean isFaltante() {
		return faltante;
	}

	public void initFaltante() {
		this.faltante = atividade.ehFaltante(getVoluntario());
	}
	
	public void initDemandas() {
		if(getVoluntario().hasRole("DEMANDA") || getVoluntario().hasRole("LIDER")) {
			demandas = demanda.minhasDemandas(getVoluntario());
		}
	}
	
	public Iterable<Demanda> getDemandas(){
		return demandas;
	}
	
	public void setDemandas(Iterable<Demanda> demandas) {
		this.demandas = demandas;
	}
	
}
