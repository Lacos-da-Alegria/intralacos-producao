package com.lacosdaalegria.intralacos.service.modules;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.demanda.Demanda;
import com.lacosdaalegria.intralacos.model.demanda.Nota;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.repository.demanda.DemandaRepository;
import com.lacosdaalegria.intralacos.repository.demanda.NotaRepository;
import com.lacosdaalegria.intralacos.repository.recurso.DiretoriaRepository;
import com.lacosdaalegria.intralacos.repository.recurso.EquipeRepository;
import com.lacosdaalegria.intralacos.service.VoluntarioService;

@Service
public class DemandaService {
	
	@Autowired
	private EquipeRepository equipe;
	@Autowired
	private DemandaRepository demanda;
	@Autowired
	private NotaRepository nota;
	@Autowired
	private VoluntarioService vService;
	@Autowired
	private DiretoriaRepository diretoria;
	
	public Equipe getEquipe(Voluntario voluntario) {
		return equipe.findByMembrosOrLider(voluntario, voluntario);
	}
	
	public Equipe addMembro(Equipe equipe, String email) {
		Voluntario membro = vService.addRole(email, "ROLE_DEMANDA");
		equipe.addMembro(membro);
		return this.equipe.save(equipe);
	}
	
	public Equipe removeMembro(Equipe equipe, Voluntario membro) {
		vService.removeRole(membro, "ROLE_DEMANDA");
		equipe.removeMembro(membro);
		return this.equipe.save(equipe);
	}
	
	public void createDemanda(Demanda demanda, Voluntario criador) {
		demanda.setCriador(criador);
		if(demanda.getResponsavel() != null)
			demanda.setStatus(1);
		this.demanda.save(demanda);
	}
	
	public Iterable<Demanda> allDemandas(Equipe equipe){
		return this.demanda.findByEquipeAndStatusNot(equipe, 4);
	}
	
	public Iterable<Equipe> getEquipesDiretoria(Voluntario diretor){
		return equipe.findByDiretoria(this.diretoria.findByDiretores(diretor));
	}
	
	public void capturarDemanda(Demanda demanda, Voluntario responsavel) {
		if(demanda.getResponsavel() == null) {
			demanda.setResponsavel(responsavel);
			demanda.setStatus(1);
			this.demanda.save(demanda);
		}
	}
	
	public void descapturarDemanda(Demanda demanda, Voluntario responsavel) {
		if(minhaDemanda(demanda, responsavel)) {
			demanda.setResponsavel(null);
			demanda.setStatus(0);
			this.demanda.save(demanda);
		}
	}
	
	public void descapturarDemanda(Demanda demanda) {
			demanda.setResponsavel(null);
			demanda.setStatus(0);
			this.demanda.save(demanda);
	}
	
	public Iterable<Nota> allNotas(Demanda demanda){
		return nota.findByDemanda(demanda);
	}
	
	public void createNotaComum(Nota nota, Voluntario criador) {
		createNota(nota, criador, 0);
	}
	
	private void createNota(Nota nota, Voluntario criador, Integer tipo) {
		nota.setCriador(criador);
		nota.setTipo(tipo);
		this.nota.save(nota);
	}
	
	public void addPendencia(Nota nota, Voluntario criador) {
		Demanda demanda = nota.getDemanda();
		if(minhaDemanda(demanda, criador)) {
			createNota(nota, criador, 1);
			demanda.setStatus(2);
			this.demanda.save(demanda);
		}
	}
	
	public void addConclusao(Nota nota, Voluntario criador) {
		Demanda demanda = nota.getDemanda();
		if(minhaDemanda(demanda, criador)) {
			createNota(nota, criador, 3);
			demanda.setStatus(3);
			this.demanda.save(demanda);
		}
	}
	
	public void addResolucao(Nota nota, Voluntario criador) {
		Demanda demanda = nota.getDemanda();
		if(minhaDemanda(demanda, criador)) {
			createNota(nota, criador, 2);
			demanda.setStatus(1);
			this.demanda.save(demanda);
		}
	}
	
	public void addReabertura(Nota nota, Voluntario criador) {
			createNota(nota, criador, 4);
			Demanda demanda = nota.getDemanda();
			demanda.setStatus(1);
			this.demanda.save(demanda);
	}
	
	public void addArquivamento(Nota nota, Voluntario criador) {
		createNota(nota, criador, 5);
		Demanda demanda = nota.getDemanda();
		demanda.setStatus(4);
		this.demanda.save(demanda);
	}
	public Equipe getEquipeById(Equipe equipe) {
		equipe = this.equipe.findById(equipe.getId()).get();
		Hibernate.initialize(equipe.getMembros());
		return equipe;
	}
	
	private boolean minhaDemanda(Demanda demanda, Voluntario responsavel) {
		return demanda.getResponsavel().getId().equals(responsavel.getId());
	}

}
