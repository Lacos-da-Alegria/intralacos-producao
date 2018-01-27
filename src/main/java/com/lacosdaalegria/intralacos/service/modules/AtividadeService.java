package com.lacosdaalegria.intralacos.service.modules;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.model.MaisLacos;
import com.lacosdaalegria.intralacos.model.atividade.Apoio;
import com.lacosdaalegria.intralacos.model.atividade.Fila;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.atividade.Registro;
import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.atividade.ApoioRepository;
import com.lacosdaalegria.intralacos.repository.atividade.HospitalRepository;
import com.lacosdaalegria.intralacos.repository.atividade.RegistroRepository;
import com.lacosdaalegria.intralacos.repository.atividade.SemanaRepository;
import com.lacosdaalegria.intralacos.repository.ongs.AgendaRepository;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Service
public class AtividadeService {

	@Autowired
	private SemanaRepository semana;
	@Autowired
	private RegistroRepository registro;
	@Autowired
	private ApoioRepository apoio;
	@Autowired
	private VoluntarioService vService;
	@Autowired
	private HospitalRepository hospital;
	@Autowired
	private OngsService ongsService;
	@Autowired
	private RecursoService recurso;
	@Autowired
	private AgendaRepository agenda;
	@Autowired
	private UserInfo info;
	
	public Semana novaSemana() {
		return semana.save(new Semana());
	}
	
	public Semana getSemana() {
		return semana.findFirstByOrderByIdDesc();
	}
	
	public Iterable<Registro> meusRegistros(Voluntario voluntario){
		return this.registro.findByVoluntarioAndStatusAndSemana(voluntario, 0, getSemana());
	}
	 
	public boolean ehFaltante(Voluntario voluntario) {
		Iterable<Registro> registros = registro.findByVoluntarioAndStatusAndCriacaoAfter(voluntario, 3, lastWeek());
		if(registros == null)
			return false;
		else {
			if(Iterables.isEmpty(registros))
				return false;
			else
				return true;
		}
	}
	
	public Registro cancelar(Voluntario voluntario, Registro registro) {
		if(registro.ehMeu(voluntario) && registro.inscricaoAberta()) {
			registro.setStatus(2);
			return this.registro.save(registro);
		}
		
		return registro;
	}
	
	private boolean podeSeIncrever(Voluntario voluntario, Hospital hospital) {
		for(Registro r : meusRegistros(voluntario)) {
			if(r.mesmoHorario(hospital))
				return false;
		}
		return true;
	}
	
	private boolean podeSeIncrever(Voluntario voluntario, Agenda agenda) {
		for(Registro r : meusRegistros(voluntario)) {
			if(r.mesmoHorario(agenda))
				return false;
		}
		return true;
	}
	
	public Registro inscrever(Voluntario voluntario, Hospital hospital) {
		initSemana(hospital);
		if(hospital.getInscricao()) {
			if(podeSeIncrever(voluntario, hospital)) {
				Registro registro = initRegistro(voluntario);
				registro.initRegistro(hospital);
				initTipoHospital(registro);
				return this.registro.save(registro);
			}
		}
		return null;
	}
	
	public Integer getPosicao(Hospital hospital, Voluntario voluntario) {
		if(!Global.rodadaRandomica()) {
			Fila fila = new Fila(hospital, registro.findFilaHospital(hospital, hospital.getSemana()));
			return fila.getPosicao(voluntario);
		} else 
			return null;
	}
	
	public Integer getPosicao(Agenda agenda, Voluntario voluntario) {
		if(!Global.rodadaRandomica()) {
			Fila fila = new Fila(agenda, registro.findFilaAcao(agenda, agenda.getSemana()));
			return fila.getPosicao(voluntario);	
		} else 
			return null;
	}
	
	private void initTipoHospital(Registro registro){
		if(registro.getVoluntario().isPromovido()) {
			if(registro.getVoluntario().hasRole("APOIO")) {
				if(recurso.ehApoio(registro.getVoluntario(), registro.getHospital()))
					registro.setTipo(2);
			} else {
				if(registro.getVoluntario().hasRole("DHOSP")) {
					registro.setTipo(3);
				} else {
					if(registro.getVoluntario().hasRole("COORD")) {
						if(recurso.ehCoordenador(registro.getVoluntario(), registro.getHospital()))
							registro.setTipo(3);
					} else {
						if(registro.getVoluntario().hasRole("DEXEC") || registro.getVoluntario().hasRole("DCOM")) {
							registro.setTipo(3);
						} else {
							registro.setTipo(0);
						}
					}
				}
			}
		} else
			registro.setTipo(1);
	}
	
	public Registro inscreverNovato(Voluntario novato, Hospital hospital) {
		initSemana(hospital);
		if(hospital.getInscricao()) {
			if(podeSeIncrever(novato, hospital)) {
				Integer novatos = this.registro.countNovatosIncritos(hospital, hospital.getSemana());
				if(novatos == null) 
					novatos = 0;
				if(novatos < hospital.getLimiteNovato()) {
					Registro registro = initRegistro(novato);
					registro.initRegistro(hospital);
					initTipoHospital(registro);
					return this.registro.save(registro);
				}
			}
		}
		return null;
	}
	
	public void retirarNovato(Voluntario novato, Hospital hospital) {
		if(hospital.getInscricao()) {
			for(Registro r : meusRegistros(novato)) {
				r.setStatus(2);
				this.registro.save(r);
			}
		}
	}
	
	public Iterable<Voluntario> novatosInscrito(Hospital hospital) {
		return this.registro.findNovatosIncritos(hospital, hospital.getSemana());
	}
	
	public Registro inscrever(Voluntario voluntario, Agenda agenda) {
		initSemana(agenda);
		if(agenda.isInscricao()) {
			if(podeSeIncrever(voluntario, agenda)) {
				Registro registro = initRegistro(voluntario, agenda);
				registro.initRegistro(agenda);
				return this.registro.save(registro);
			}
		}
		return null;
	}
	
	public Fila getFilaAtividade(Hospital hospital) {
		initSemana(hospital);
		return new Fila(hospital, registro.findFilaHospital(hospital, hospital.getSemana()));
	}
	
	public void initSemana(Hospital hospital) {
		if(hospital.getSemana() == null) {
			hospital.setSemana(getSemana());
			this.hospital.save(hospital);
		}
	}
	
	public void initSemana(Agenda agenda) {
		if(agenda.getSemana()==null) {
			agenda.setSemana(getSemana());
			ongsService.saveAgenda(agenda);
		}
	}
	
	public boolean finalizaChamada(Hospital hospital) {
		Fila fila = new Fila(hospital, registro.findFilaHospital(hospital, hospital.getSemana()));
		if(fila.finalizada()) {
			hospital.setChamada(false);
			if(!hospital.essaSemana(getSemana())) {
				hospital.setInscricao(true);
				hospital.setSemana(null);
			}
			this.hospital.save(hospital);
			promoveNovatos(fila.novatosQueForam());
			desativaNovatos(fila.novatosNaoForam());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean finalizaChamada(Agenda agenda) {
		Fila fila = new Fila(agenda, registro.findFilaAcao(agenda, agenda.getSemana()));
		if(fila.finalizada()) {
			agenda.setChamada(false);
			this.agenda.save(agenda);
			return true;
		}
		return false;
	}
	
	private void promoveNovatos(Set<Voluntario> novatos) {
		for(Voluntario n : novatos) {
			vService.promoteNovato(n);
		}
	}
	
	private void desativaNovatos(Set<Voluntario> novatos) {
		for(Voluntario n : novatos) {
			vService.desativaNovato(n);
		}
	}
	
	public Fila getFilaAtividade(Agenda agenda) {
		initSemana(agenda);
		return new Fila(agenda, registro.findFilaAcao(agenda, agenda.getSemana()));
	}
	
	private Registro initRegistro(Voluntario voluntario) {
		Registro registro = new Registro();
		registro.setVoluntario(voluntario);
		registro.setSemana(getSemana());
		registro.initPosicao(info.isFaltante());
		return registro;
	}
	
	private Registro initRegistro(Voluntario voluntario, Agenda agenda) {
		Registro registro = new Registro();
		registro.setVoluntario(voluntario);
		registro.setSemana(agenda.getSemana());
		registro.setPosicao(10001);
		return registro;
	}
	
	public Iterable<Apoio> findApoios(Hospital hospital){
		return apoio.findByHospital(hospital);
	}
	
	public void addApoio(Hospital hospital, String email) {
		Voluntario voluntario = vService.findByEmail(email);
		if(voluntario != null) {
			if(this.apoio.findByVoluntario(voluntario) == null) {
				vService.addRole(voluntario, "ROLE_APOIO");
				this.apoio.save(initApoio(hospital, voluntario));
			}
		}
	}
	
	public void removeApoio(Apoio apoio) {
		vService.removeRole(apoio.getVoluntario(), "ROLE_APOIO");
		this.apoio.delete(apoio);
	}
	
	private Apoio initApoio(Hospital hospital, Voluntario voluntario) {
		Apoio apoio = new Apoio();
		apoio.setHospital(hospital);
		apoio.setVoluntario(voluntario);
		return apoio;
	}
	
	public void ativaAtividade(Coordenador coordenador, String senha) {
		if(vService.validaSenha(coordenador.getVoluntario(), senha)) {
			Hospital hospital = coordenador.getHospital();
			hospital.setStatus(1);
			this.hospital.save(hospital);
		}
	}
	
	public void desativaAtividade(Coordenador coordenador, String senha) {
		if(vService.validaSenha(coordenador.getVoluntario(), senha)) {
			Hospital hospital = coordenador.getHospital();
			hospital.setStatus(2);
			this.hospital.save(hospital);
		}
	}
	
	public Registro presenteChamada(Registro registro) {
		registro.setStatus(1);
		return this.registro.save(registro);
	}
	
	public Registro ausenteChamada(Registro registro) {
		registro.setStatus(3);
		return this.registro.save(registro);
	}
	
	public void liberarInscricao(Semana semana) {
		Iterable<Hospital> hospitais = this.hospital.findByStatusAndChamadaFalse(1);
		for(Hospital hospital : hospitais) {
			hospital.setInscricao(true);
			hospital.setSemana(getSemana());
		}
		this.hospital.saveAll(hospitais);
	}
	
	public void atividadesMatutinas() {
		Semana semana = getSemana();
		hospitaisAgora(this.hospital.findByDiaAndPeriodo(getDia(), 1), semana);
		acoesAgora(ongsService.getAcoesAtivas(), semana, 1);
	}
	
	public void atividadesVespertinas() {	
		Semana semana = getSemana();
		hospitaisAgora(this.hospital.findByDiaAndPeriodo(getDia(), 2), semana);
		acoesAgora(ongsService.getAcoesAtivas(), semana, 2);
	}
	
	public void atividadesNoturnas() {
		Semana semana = getSemana();
		hospitaisAgora(this.hospital.findByDiaAndPeriodo(getDia(), 3), semana);
		acoesAgora(ongsService.getAcoesAtivas(), semana, 3);
	}
	
	public void initMaisLacos(MaisLacos maisLacos, Voluntario voluntario) {
		if(voluntario.isPromovido()) {
			maisLacos.setHospitais(registro.visitaHospitais(voluntario));
			maisLacos.setOngs(registro.visitaAcoes(voluntario));
		} 
		
		maisLacos.setHoras(registro.visitaEsseAno(getAno())*2);
	}
	
	private void hospitaisAgora(Iterable<Hospital> hospitais, Semana semana) {
		for(Hospital h : hospitais) {
			h.setChamada(true);
			h.setInscricao(false);
			h.setSemana(semana);
		} 
		this.hospital.saveAll(hospitais);
	}
	
	private void acoesAgora(Iterable<Agenda> agendas, Semana semana, Integer periodo) {
		for(Agenda a : agendas) {
			if(a.ehAgora(getDia(), periodo)) {
				a.setChamada(true);
				a.setInscricao(false);
			}
		}
		ongsService.saveAllAgendas(agendas);
	}
	
	private Integer getDia() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	private Integer getAno() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.YEAR);
	}
	
	private Date lastWeek() {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		c.add(Calendar.DATE, -6);
		return c.getTime();
	}


}
