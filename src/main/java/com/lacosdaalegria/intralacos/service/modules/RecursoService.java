package com.lacosdaalegria.intralacos.service.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.atividade.Apoio;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.recurso.ControleNovato;
import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.model.recurso.Diretoria;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.repository.atividade.ApoioRepository;
import com.lacosdaalegria.intralacos.repository.recurso.ControleNovatoRepository;
import com.lacosdaalegria.intralacos.repository.recurso.CoordenadorRepository;
import com.lacosdaalegria.intralacos.repository.recurso.DiretoriaRepository;
import com.lacosdaalegria.intralacos.repository.recurso.EquipeRepository;
import com.lacosdaalegria.intralacos.service.VoluntarioService;

@Service
public class RecursoService {

	@Autowired
	private DiretoriaRepository diretoria;
	@Autowired
	private VoluntarioService vService;
	@Autowired
	private EquipeRepository equipe;
	@Autowired
	private CoordenadorRepository coordenador;
	@Autowired
	private ControleNovatoRepository controleNovato;
	@Autowired
	private ApoioRepository apoio;
	
	public void saveDiretoria(Diretoria diretoria) {
		this.diretoria.save(diretoria);
	}
	
	public Iterable<Diretoria> allDiretorias(){
		return this.diretoria.findAll();
	}
	
	public Diretoria findDiretoria(Voluntario diretor) {
		return diretoria.findByDiretores(diretor);
	}
	
	public void addDiretor(Diretoria diretoria, String email) {
		Voluntario diretor = vService.findByEmail(email);
		vService.addRole(diretor, diretoria.getRole());
		vService.addRole(diretor, "ROLE_DIRETOR");
		diretoria.addDiretor(diretor);
		this.diretoria.save(diretoria);
	}
	
	public void removeDiretor(Diretoria diretoria, Voluntario diretor) {
		diretoria.removeDiretor(diretor);
		vService.removeRole(diretor, diretoria.getRole());
		vService.removeRole(diretor, "ROLE_DIRETOR");
		this.diretoria.save(diretoria);
	}
	
	public void createEquipe(Equipe equipe, String email) {
		Voluntario lider = null;
		if(email != null) {
			lider = vService.findByEmail(email);
			if(lider != null) {
				vService.addRole(lider, "ROLE_LIDER");
				equipe.setLider(lider);
			}
		}
		this.equipe.save(equipe);
	}
	
	public void updateEquipe(Equipe equipe, String email) {
		Voluntario lider = null;
		Equipe equipe_ = this.equipe.findById(equipe.getId()).get();
		
		if(email != null) {
			lider = vService.findByEmail(email);
			if(lider != null && !lider.getId().equals(equipe_.getLider().getId())) {
				vService.removeRole(equipe.getLider(), "ROLE_LIDER");
				vService.addRole(lider, "ROLE_LIDER");
				equipe.setLider(lider);
			}
		}
		
		equipe_.updateEquipe(equipe);
		this.equipe.save(equipe_);
	}
	
	public void createCoordenador(Hospital hospital, String email) {
		Voluntario voluntario = vService.findByEmail(email);
		if(voluntario != null) {
			if(findCoordenador(voluntario) == null) {
				vService.addRole(voluntario, "ROLE_COORD");
				this.coordenador.save(initCoordenacao(hospital, voluntario));
			}
		}
	}
	
	public void removeCoordenador(Voluntario voluntario) {
		Coordenador coordenador =  this.coordenador.findByVoluntario(voluntario);
			this.coordenador.delete(coordenador);
			vService.removeRole(voluntario, "ROLE_COORD");
		
	}
	
	public Iterable<Coordenador> activeCoordenadores(){
		return this.coordenador.findAll();
	}
	
	public void createControlador(Hospital hospital, String email) {
		Voluntario voluntario = vService.findByEmail(email);
		if(voluntario != null) {
			if(findControlador(voluntario) == null) {
				vService.addRole(voluntario, "ROLE_CONTROL");
				this.controleNovato.save(initControlador(hospital, voluntario));
			}
		}
	}
	
	public void removeControlador(Voluntario voluntario) {
		ControleNovato controlador =  this.controleNovato.findByVoluntario(voluntario);
		this.controleNovato.delete(controlador);
		vService.removeRole(voluntario, "ROLE_CONTROL");
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
