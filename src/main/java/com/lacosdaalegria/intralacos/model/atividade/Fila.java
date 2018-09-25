package com.lacosdaalegria.intralacos.model.atividade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

import lombok.Getter;
import lombok.Setter;

public class Fila {

	@Getter @Setter private Agenda agenda;
	@Getter @Setter private Hospital hospital;
	@Getter @Setter private List<Registro> registros;
	@Setter private List<Voluntario> lista;
	@Setter private List<Voluntario> equipe;
	@Setter private List<Voluntario> espera;
	@Setter private List<Voluntario> novatos;
	@Setter private List<Voluntario> especial;
	@Setter private List<Registro> chamada;
	
	
	public Fila(Hospital hospital, List<Registro> registros) {
		super();
		this.hospital = hospital;
		this.registros = registros;		
		initFilaHospitais();
	}
	
	public Fila(Agenda agenda, List<Registro> registros) {
		super();
		this.agenda = agenda;
		this.registros = registros;
		initFilaAcoes();
	}
	
	public Fila() {
		super();
	}
	
	public boolean finalizada() {
		return !getChamada().stream().filter(r -> r.getStatus().equals(0)).findFirst().isPresent();
	}
	
	public Registro getRegistro(Voluntario voluntario) {
		return this.chamada.stream().filter(v -> v.ehMeu(voluntario)).findFirst().orElse(null);
	}
	
	public Set<Voluntario> novatosQueForam(){
		Set<Voluntario> novatos = new HashSet<>();
		for(Registro r : chamada) {
			if(r.isNovato() && r.getStatus().equals(1))
				novatos.add(r.getVoluntario());
		}
		return novatos;
	}
	
	public Set<Voluntario> novatosNaoForam(){
		Set<Voluntario> novatos = new HashSet<>();
		for(Registro r : chamada) {
			if(r.isNovato() && r.getStatus().equals(3))
				novatos.add(r.getVoluntario());
		}
		return novatos;
	}
	
	private void initFilaHospitais() {
		for(Registro registro : registros) {
			if(registro.isNormal()) {
				addNormalHospital(registro);
			} else {
				if (registro.isEquipe()) {
					addEquipeHospital(registro);
				} else {
					if (registro.isNovato()) {
						getNovatos().add(registro.getVoluntario());
						getChamada().add(registro);
					} else {
						getEspecial().add(registro.getVoluntario());
						getChamada().add(registro);
					}
				}
			}
		}
		addEspecial();
	}
	
	private void addEspecial() {
		getEquipe().addAll(getEspecial());
		this.especial = null;
		this.registros = null;
	}
	
	private void addNormalHospital(Registro registro) {
		if(hospitalLotado())
			getEspera().add(registro.getVoluntario());
		else {
			getLista().add(registro.getVoluntario());
			getChamada().add(registro);
		}
	}
	
	private void addEquipeHospital(Registro registro) {
		if(equipeLotada())
			addNormalHospital(registro);
		else {
			getEquipe().add(registro.getVoluntario());
			getChamada().add(registro);
		}
	}
	
	private void addNormalAcao(Registro registro) {
		if(acaoLotada())
			getEspera().add(registro.getVoluntario());
		else {
			getLista().add(registro.getVoluntario());
			getChamada().add(registro);
		}
	}
	
	
	private void initFilaAcoes() {
		for(Registro registro : registros) {
			if(registro.isNormal()) {
				addNormalAcao(registro);
			} else {
				if (registro.isEquipe()) {
					getEquipe().add(registro.getVoluntario());
					getChamada().add(registro);
				} else {
					if (registro.isNovato()) {
						if(agenda.getNovatos().intValue() > getNovatos().size()) {
							getNovatos().add(registro.getVoluntario());
							getChamada().add(registro);
						}
					} else {
						getEspecial().add(registro.getVoluntario());
						getChamada().add(registro);
					}
				}
			}
		}
		addEspecial();
	}
	
	private boolean hospitalLotado() {
		return hospital.getLimiteVoluntario().intValue() <= getLista().size();
	}
	
	private boolean acaoLotada() {
		return agenda.getVoluntarios().intValue() <= getLista().size();
	}
	
	private boolean equipeLotada() {
		return hospital.getLimiteApoio().intValue() <= getEquipe().size();
	}
	
	public Integer getPosicao(Voluntario voluntario) {
		for(int i = 0; i < getEspera().size() ; i++){
			if(getEspera().get(i).getId().equals(voluntario.getId()))
				return i+1;
		}
		return -1;
	}
	
	public Integer getPosicaoNovato(Voluntario voluntario) {
		for(int i = 0; i < getNovatos().size() ; i++){
			if(getNovatos().get(i).getId().equals(voluntario.getId()))
				return i+1;
		}
		return -1;
	}
	
	/*
	 * ======================================================================================
	 * ============================== Getters and Setters ===================================
	 * ======================================================================================
	 */


	public List<Voluntario> getLista() {
		if(lista == null)
			lista = new ArrayList<>();
		return lista;
	}

	public List<Voluntario> getEquipe() {
		if(equipe == null)
			equipe = new ArrayList<>();
		return equipe;
	}

	public List<Voluntario> getEspera() {
		if(espera == null)
			espera = new ArrayList<>();
		return espera;
	}

	public List<Voluntario> getNovatos() {
		if(novatos == null)
			novatos = new ArrayList<>();
		return novatos;
	}
	
	private List<Voluntario> getEspecial() {
		if(especial == null)
			especial = new ArrayList<>();
		return especial;
	}

	public List<Registro> getChamada() {
		if(chamada == null)
			chamada = new ArrayList<>();
		return chamada;
	}
}
