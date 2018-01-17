package com.lacosdaalegria.intralacos.model.atividade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;

public class Fila {
	
	private Agenda agenda;
	private Hospital hospital;
	private List<Registro> registros;
	private List<Voluntario> lista;
	private List<Voluntario> equipe;
	private List<Voluntario> espera;
	private List<Voluntario> novatos;
	private List<Voluntario> especial;
	private List<Registro> chamada;
	
	
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
	
	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public List<Voluntario> getLista() {
		if(lista == null)
			lista = new ArrayList<>();
		return lista;
	}

	public void setLista(List<Voluntario> lista) {
		this.lista = lista;
	}

	public List<Voluntario> getEquipe() {
		if(equipe == null)
			equipe = new ArrayList<>();
		return equipe;
	}

	public void setEquipe(List<Voluntario> equipe) {
		this.equipe = equipe;
	}

	public List<Voluntario> getEspera() {
		if(espera == null)
			espera = new ArrayList<>();
		return espera;
	}

	public void setEspera(List<Voluntario> espera) {
		this.espera = espera;
	}

	public List<Voluntario> getNovatos() {
		if(novatos == null)
			novatos = new ArrayList<>();
		return novatos;
	}

	public void setNovatos(List<Voluntario> novatos) {
		this.novatos = novatos;
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

	public void setChamada(List<Registro> chamada) {
		this.chamada = chamada;
	}

}
