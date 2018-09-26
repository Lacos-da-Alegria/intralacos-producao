package com.lacosdaalegria.intralacos.service.modules;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lacosdaalegria.intralacos.model.atividade.Apoio;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.recurso.ControleNovato;
import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.model.recurso.Diretoria;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.model.usuario.RoleEnum;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.atividade.ApoioRepository;
import com.lacosdaalegria.intralacos.repository.recurso.ControleNovatoRepository;
import com.lacosdaalegria.intralacos.repository.recurso.CoordenadorRepository;
import com.lacosdaalegria.intralacos.repository.recurso.DiretoriaRepository;
import com.lacosdaalegria.intralacos.repository.recurso.EquipeRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecursoService {

	private @NonNull DiretoriaRepository diretoria;
	private @NonNull VoluntarioService vService;
	private @NonNull EquipeRepository equipe;
	private @NonNull CoordenadorRepository coordenador;
	private @NonNull ControleNovatoRepository controleNovato;
	private @NonNull ApoioRepository apoio;
	
	public void saveDiretoria(Diretoria diretoria) {
		this.diretoria.save(diretoria);
	}
	
	public Iterable<Diretoria> allDiretorias(){
		return this.diretoria.findAll();
	}
	
	public Diretoria findDiretoria(Voluntario diretor) {
		return diretoria.findByDiretores(diretor);
	}

	@Transactional
	public void addDiretor(Diretoria diretoria, String email) {
		Voluntario diretor = vService.findByEmail(email);
		vService.addRole(diretor, RoleEnum.getByPapel(diretoria.getRole()));
		vService.addRole(diretor, RoleEnum.DIRETOR);
		diretoria.addDiretor(diretor);
		this.diretoria.save(diretoria);
	}

    @Transactional
	public void removeDiretor(Diretoria diretoria, Voluntario diretor) {
		diretoria.removeDiretor(diretor);
		vService.removeRole(diretor, RoleEnum.getByPapel(diretoria.getRole()));
		vService.removeRole(diretor, RoleEnum.DIRETOR);
		this.diretoria.save(diretoria);
	}


	public void createEquipe(Equipe equipe, String email) {
		Voluntario lider;
		if(email != null) {
			lider = vService.findByEmail(email);
			if(lider != null) {
				vService.addRole(lider, RoleEnum.LIDER);
				equipe.setLider(lider);
				equipe.addMembro(lider);
			}
		}
		this.equipe.save(equipe);
	}
	
	public Equipe updateEquipe(Equipe equipe, String email) {
		Voluntario lider = null;
		Equipe equipe_ = this.equipe.findById(equipe.getId()).get();

		if(email != null) {
			lider = vService.findByEmail(email);
			
			if(updateLider(lider, equipe_)) {
				if(equipe.getLider()!=null) {
					vService.removeRole(equipe.getLider(), RoleEnum.LIDER);
					equipe_.removeMembro(equipe.getLider());
				}
				vService.addRole(lider, RoleEnum.LIDER);
				equipe_.setLider(lider);
				equipe_.addMembro(lider);
			}
		}
		
		equipe_.updateEquipe(equipe);
		this.equipe.save(equipe_);
		Hibernate.initialize(equipe_.getMembros());
		
		return equipe_;
	}
	
	private boolean updateLider(Voluntario lider, Equipe equipe) {
		return lider != null && 
					(equipe.getLider() == null || 
						!lider.getId().equals(equipe.getLider().getId()));
	}

	@Transactional
	public void createCoordenador(Hospital hospital, String email) {
		Voluntario voluntario = vService.findByEmail(email);
		if(voluntario != null) {
			if(findCoordenador(voluntario) == null) {
				vService.addRole(voluntario, RoleEnum.COORDENADOR);
				this.coordenador.save(initCoordenacao(hospital, voluntario));
			}
		}
	}

	@Transactional
	public void removeCoordenador(Voluntario voluntario) {
		Coordenador coordenador =  this.coordenador.findByVoluntario(voluntario);
        this.coordenador.delete(coordenador);
        vService.removeRole(voluntario, RoleEnum.COORDENADOR);
		
	}
	
	public Iterable<Coordenador> activeCoordenadores(){
		return this.coordenador.findAll();
	}

	@Transactional
	public void createControlador(Hospital hospital, String email) {
		Voluntario voluntario = vService.findByEmail(email);
		if(voluntario != null) {
			if(findControlador(voluntario) == null) {
				vService.addRole(voluntario, RoleEnum.CONTROLE_NOVATOS);
				this.controleNovato.save(initControlador(hospital, voluntario));
			}
		}
	}

	@Transactional
	public void removeControlador(Voluntario voluntario) {
		ControleNovato controlador =  this.controleNovato.findByVoluntario(voluntario);
		this.controleNovato.delete(controlador);
		vService.removeRole(voluntario, RoleEnum.CONTROLE_NOVATOS);
	}
	
	public ControleNovato findControlador(Voluntario voluntario) {
		return this.controleNovato.findByVoluntario(voluntario);
	}
	
	public Iterable<ControleNovato> activeControleNovato(){
		return this.controleNovato.findAll();
	}
	
	public Coordenador findCoordenador(Voluntario voluntario) {
		return this.coordenador.findByVoluntario(voluntario);
	}
	
	private Coordenador initCoordenacao(Hospital hospital, Voluntario voluntario) {
		Coordenador coordenador = new Coordenador();
		coordenador.setHospital(hospital);
		coordenador.setVoluntario(voluntario);
		return coordenador;
	}
	
	private ControleNovato initControlador(Hospital hospital, Voluntario voluntario) {
		ControleNovato controlador = new ControleNovato();
		controlador.setHospital(hospital);
		controlador.setVoluntario(voluntario);
		return controlador;
	}
	
	public boolean ehCoordenador(Voluntario voluntario, Hospital hospital) {
		Coordenador coordenador = this.coordenador.findByVoluntario(voluntario);
		return coordenador.getHospital().getId().equals(hospital.getId());
	}
	
	public boolean ehApoio(Voluntario voluntario, Hospital hospital) {
		Apoio apoio = this.apoio.findByVoluntario(voluntario);
		return apoio.getHospital().getId().equals(hospital.getId());
	}
}
