package com.lacosdaalegria.intralacos.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.recurso.Equipe;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InfoDiretoria {

	private Equipe equipe;
	
	public Equipe getEquipe() {
		if(equipe == null)
			return new Equipe();
		return equipe;
	}
	
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	
	public void initEquipe(Equipe equipe) {
		if(this.equipe == null)
			this.equipe= equipe;
	}	
}
