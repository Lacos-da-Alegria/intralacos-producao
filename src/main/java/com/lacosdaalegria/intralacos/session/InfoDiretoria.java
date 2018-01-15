package com.lacosdaalegria.intralacos.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.recurso.Equipe;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class InfoDiretoria {

	private Equipe equipe;
	
	public Equipe getEquipe(Iterable<Equipe> equipes) {
		initEquipe(equipes);
		return equipe;
	}
	
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	
	private void initEquipe(Iterable<Equipe> equipes) {
		if(equipe == null) {
			if(equipes != null) 
				equipe = equipes.iterator().next();
			else
				equipe = new Equipe();
		}
	}
}
