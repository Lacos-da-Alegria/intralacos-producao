package com.lacosdaalegria.intralacos.dev;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.model.usuario.Role;
import com.lacosdaalegria.intralacos.repository.atividade.HospitalRepository;
import com.lacosdaalegria.intralacos.repository.atividade.SemanaRepository;
import com.lacosdaalegria.intralacos.repository.usuario.RegiaoRepository;
import com.lacosdaalegria.intralacos.repository.usuario.RoleRepository;

@Component
public class InitData {
	
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	HospitalRepository hospRepo;
	@Autowired
	RegiaoRepository raRepo;
	@Autowired
	SemanaRepository seRepo;
	
	public void start() {
		
		System.out.println("Inicialização de Dados!");
		
		if(Iterables.size(roleRepo.findAll()) == 0) {
			initRoles();
		}
		if(Iterables.size(hospRepo.findAll()) == 0) {
			initHospitais();	
		}
		if(Iterables.size(raRepo.findAll()) == 0) {
			initRas();
		}
		if(Iterables.size(seRepo.findAll()) == 0) {
			initSemana();
		}
	}
	
	private void initSemana() {
		seRepo.save(new Semana());
	}
	
	//Metodo para iniciação dos dados 
	private void initHospitais() {
		
		Hospital ativ = getHospital();
		
		ativ.setNome("Hospital Regional do Gama");
		ativ.setTag("HRG");
		
		hospRepo.save(ativ);
		
		ativ = getHospital();

		ativ.setNome("Hospital Regional de Taguatinga");
		ativ.setTag("HRT");
		
		hospRepo.save(ativ);
		
		ativ = getHospital();
		
		ativ.setNome("Hospital Universitário de Brasília");
		ativ.setTag("HUB");
		
		hospRepo.save(ativ);
		
		ativ = getHospital();
		
		ativ.setNome("Hospital das Forças Armadas");
		ativ.setTag("HFA");
		
		hospRepo.save(ativ);
		
		ativ = getHospital();
		
		ativ.setNome("Hospital Regional de Planaltina");
		ativ.setTag("HRP");
		
		hospRepo.save(ativ);
		
		ativ = getHospital();
		
		ativ.setNome("Hospital Regional de Sobradinho");
		ativ.setTag("HRS");
		
		hospRepo.save(ativ);
		
		ativ = getHospital();
		
		ativ.setNome("Hospital Regional da Asa Norte");
		ativ.setTag("HRAN");
		
		hospRepo.save(ativ);
	}
	
	private Hospital getHospital() {
		
		Hospital ativ = new Hospital();
		ativ.setStatus(1);
		ativ.setDescricao("Descrição Atividade");
		ativ.setEndereco("Endereço Atividade");
		ativ.setLocalPreparo("Local de Preparo");
		ativ.setHorario("Horario da Atividade");
		ativ.setDia(7);
		ativ.setPeriodo(2);
		ativ.setLimiteVoluntario(22);
		ativ.setLimiteApoio(8);
		ativ.setLimiteNovato(8);
		
		return ativ;
	}
	
	private void initRoles() {
		
		roleRepo.save(initRole("ROLE_ACEITE"));
		roleRepo.save(initRole("ROLE_NOVATO"));
		roleRepo.save(initRole("ROLE_VOLUNTARIO"));
		roleRepo.save(initRole("ROLE_ADMIN"));
		roleRepo.save(initRole("ROLE_ATEND"));
		
		roleRepo.save(initRole("ROLE_DEXEC"));
		roleRepo.save(initRole("ROLE_DHOSP"));
		roleRepo.save(initRole("ROLE_DONGS"));
		roleRepo.save(initRole("ROLE_DCOM"));
		
		roleRepo.save(initRole("ROLE_ONGS"));
		
		roleRepo.save(initRole("ROLE_DIRETOR"));
		
		roleRepo.save(initRole("ROLE_LIDER"));
		roleRepo.save(initRole("ROLE_DEMANDA"));
		
		roleRepo.save(initRole("ROLE_COORD"));
		roleRepo.save(initRole("ROLE_CONTROL"));
		roleRepo.save(initRole("ROLE_APOIO"));
		
		roleRepo.save(initRole("ROLE_SUSTENTA"));
		
		roleRepo.save(initRole("ROLE_CAPACITA"));
		
	}
	
	private Role initRole(String name) {
		Role role = new Role();
		role.setRole(name);
		return role;
	}
	
	//Metodo de desenvolvimetno para criar as Regioes Administrativas
	private void initRas() {
		
		Set<String> ras = new HashSet<>();

		//Primeira carga
		 ras.add("Águas Claras");
		 ras.add("Asa Norte");
		 ras.add("Asa Sul");
		 ras.add("Brazlándia"); 
		 ras.add("Candangolândia");
		 ras.add("Ceilândia");
		 ras.add("Cruzeiro");
		 ras.add("Estrutural");
		 ras.add("Fercal");
		 ras.add("Gama");
		 ras.add("Guará");
		 ras.add("Jardim Botânico"); 
		 ras.add("Itapoã");
		 ras.add("Lago Norte");
		 ras.add("Lagoa Sul");
		 ras.add("Núcleo Bandeirante");
		 ras.add("Paranoá");
		 ras.add("Park Way");
		 ras.add("Planaltina");
		 ras.add("Recanto das Emas");
		 ras.add("Riacho Fundo");
		 ras.add("Riacho Fundo II");
		 ras.add("Samambaia");
		 ras.add("Santa Maria");
		 ras.add("São Sebastião");
		 ras.add("Sobradinho");
		 ras.add("Sobradinho II");
		 ras.add("Taguatinga");
		 ras.add("Varjão");
		 ras.add("Vicente Pires");
		 ras.add("Entorno Saida Norte");
		 ras.add("Entorno Saida Sul");
	
		 for (String ra : ras){
			 
			 Regiao ra1 = new Regiao();
			 
			 ra1.setNome(ra);
			 
			 raRepo.save(ra1);
			 
		 }
		
	}

}
