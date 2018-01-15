package com.lacosdaalegria.intralacos.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lacosdaalegria.intralacos.model.Fila;
import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.model.atividade.Apoio;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.atividade.Registro;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.service.HospitalService;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.service.modules.RecursoService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class AtividadeController {
	
	@Autowired
	private HospitalService hospital;
	@Autowired
	private AtividadeService service;
	@Autowired
	private OngsService ongs;
	@Autowired
	private UserInfo info;
	@Autowired
	private RecursoService recurso;
	
	@GetMapping("/hospitais/controle")
	public String hospitaisPage(Model model) {
		model.addAttribute("hospitais", hospital.getAll());
		return "admin/hospitais";
	}
	
	@GetMapping("/voluntario/lista/atividade")
	public String listaAtividade(Model model) {
		model.addAttribute("hospitais", hospital.getAllActive());
		model.addAttribute("acoes", ongs.getAcoes());
		model.addAttribute("rodada", Global.rodadaRandomica());
		model.addAttribute("fila", new Fila());
		return "atividade/lista";
	}
	
	@GetMapping("/voluntario/lista/hospital")
	public String listaHospital(Hospital hospital, Model model) {
		if(Global.rodadaRandomica() || hospital == null){
			return "redirect:/voluntario/lista/atividade";
		}
		
		model.addAttribute("rodada", Global.rodadaRandomica());
		model.addAttribute("hospitais", this.hospital.getAllActive());
		model.addAttribute("acoes", ongs.getAcoes());
		model.addAttribute("fila", service.getFilaAtividade(hospital));
		model.addAttribute("hospital_", hospital);		

		return "atividade/lista";
	}
	
	@GetMapping("/voluntario/lista/acao")
	public String listaAcao(Agenda agenda, Model model) {
		if(Global.rodadaRandomica() || agenda == null){
			return "redirect:/voluntario/lista/atividade";
		}
		
		model.addAttribute("rodada", Global.rodadaRandomica());
		model.addAttribute("hospitais", hospital.getAllActive());
		model.addAttribute("acoes", ongs.getAcoes());
		model.addAttribute("fila", service.getFilaAtividade(agenda));
		model.addAttribute("agenda", agenda);
		
		return "atividade/lista";
	}
	
	@GetMapping("/voluntario/posicao/hospital")
	public @ResponseBody Integer posicaoHospital(Hospital hospital) {
		return service.getPosicao(hospital, info.getVoluntario());
	}
	
	@GetMapping("/voluntario/posicao/acao")
	public @ResponseBody Integer posicaoAcao(Agenda agenda) {
		return service.getPosicao(agenda, info.getVoluntario());
	}
	
	
	
	@GetMapping("/voluntario/inscrever/hospital")
	public @ResponseBody Registro inscreverHospital(Hospital hospital) {
		return service.inscrever(info.getVoluntario(), hospital);
	}
	
	@GetMapping("/voluntario/inscrever/acao")
	public @ResponseBody Registro inscreverAcao(Agenda agenda) {
		return service.inscrever(info.getVoluntario(), agenda);
	}
	
	@GetMapping("/voluntario/cancelar")
	public @ResponseBody Registro cancelarAtividade(Registro registro) {
		return service.cancelar(info.getVoluntario(), registro);
	}
	
	@GetMapping("/voluntario/info/hospital")
	public @ResponseBody Hospital infoHospital(Hospital hospital) {
		return hospital;
	}
	
	@GetMapping("/voluntario/info/agenda")
	public @ResponseBody Agenda infoAgenda(Agenda agenda) {
		return agenda;
	}
	
	@GetMapping("/voluntario/hospitais")
	public @ResponseBody Iterable<Hospital> hospitais() {
		return hospital.getAllActive();
	}
	
	@GetMapping("/voluntario/acoes")
	public @ResponseBody Iterable<Agenda> acoes() {
		return ongs.getAcoes();
	}
	
	@GetMapping("/voluntario/registros")
	public @ResponseBody Iterable<Registro> registros() {
		return service.meusRegistros(info.getVoluntario());
	}
	
	@GetMapping("/coordenador/home")
	public String atividadePage(Model model) {
		
		Coordenador coordenador = recurso.findCoordenador(info.getVoluntario());
		
		model.addAttribute("hospital", coordenador.getHospital());
		model.addAttribute("fila", service.getFilaAtividade(coordenador.getHospital()));
		model.addAttribute("apoios", service.findApoios(coordenador.getHospital()));
		model.addAttribute("rodada", Global.rodadaRandomica());
		
		return "atividade/home";
	}
	
	@PostMapping("/coordenador/add/apoio")
	public String addApoio(String email) {
		Coordenador coordenador = recurso.findCoordenador(info.getVoluntario());
		service.addApoio(coordenador.getHospital(), email);
		return "redirect:/coordenador/home";
	}
	
	@PostMapping("/coordenador/remove/apoio")
	public String removeApoio(Apoio apoio) {
		service.removeApoio(apoio);
		return "redirect:/coordenador/home";
	}
	
	@PostMapping("/coordenador/desativa/atividade")
	public String desativaAtividade(String senha) {
		Coordenador coordenador = recurso.findCoordenador(info.getVoluntario());
		service.desativaAtividade(coordenador, senha);
		return "redirect:/coordenador/home";
	}
	
	@PostMapping("/coordenador/ativa/atividade")
	public String ativaAtividade(String senha) {
		Coordenador coordenador = recurso.findCoordenador(info.getVoluntario());
		service.ativaAtividade(coordenador, senha);
		return "redirect:/coordenador/home";
	}
	
	@GetMapping("/coordenador/chamada/presente")
	public @ResponseBody Registro voluntarioPresente(Registro registro) {
		if(registro.chamadaAberta())
			return service.presenteChamada(registro);
		else 
			return null;
	}
	
	@GetMapping("/coordenador/chamada/ausente")
	public @ResponseBody Registro VoluntarioAusente(Registro registro) {
		if(registro.chamadaAberta())
			return service.ausenteChamada(registro);
		else
			return null;
	}
	
	@GetMapping("/coordenador/finalizar/chamada")
	public String voluntarioPresente(RedirectAttributes redirectAttrs) {
		Coordenador coordenador = recurso.findCoordenador(info.getVoluntario());
		if(service.finalizaChamada(coordenador.getHospital())) {
			redirectAttrs.addFlashAttribute("successMessage", "Chamada finalizada com sucesso");
		} else {
			redirectAttrs.addFlashAttribute("erroMessage", "Ainda há voluntários na chamada");
		}
		return "redirect:/coordenador/home";
	}
	
	@PostMapping("/coordenador/atualizar/hospital")
	public String updateHospitalPost(Hospital hospital, RedirectAttributes redirectAttrs) {
		redirectAttrs.addFlashAttribute("successMessage", "Informações do Hospital foram atualizadas com sucesso!");
		this.hospital.updateHospital(hospital);
		return "redirect:/coordenador/home";
	}
	

}
