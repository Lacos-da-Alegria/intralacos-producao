package com.lacosdaalegria.intralacos.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.demanda.Demanda;
import com.lacosdaalegria.intralacos.model.demanda.Nota;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.service.modules.DemandaService;
import com.lacosdaalegria.intralacos.session.InfoDiretoria;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class DemandaController {
	
	@Autowired
	private DemandaService service;
	@Autowired
	private UserInfo info;
	@Autowired
	private InfoDiretoria infoDiretoria;
	
	@GetMapping("/demanda/page")
	public String demandaPage(Model model) {
		
		Equipe equipe = treatEquipe(model);
		model.addAttribute("equipe", equipe);
		model.addAttribute("demandas", service.allDemandas(equipe));

		return "demandas/home";
	}
	
	private Equipe treatEquipe(Model model) {
		if(info.getVoluntario().hasRole("DIRETOR")) {
			Iterable<Equipe> equipes = service.getEquipesDiretoria(info.getVoluntario()); 
			model.addAttribute("equipes", equipes);
			infoDiretoria.initEquipe(equipes.iterator().next());
			return infoDiretoria.getEquipe();
		} else {
			return service.getEquipe(info.getVoluntario());
		}
	}
	
	private void updateEquipe(Equipe equipe) {
		if(info.getVoluntario().hasRole("DIRETOR"))
			infoDiretoria.setEquipe(equipe);
	}
	
	@GetMapping("/diretor/change/equipe")
	public String changeEquipe(Equipe equipe) {
		updateEquipe(service.getEquipeById(equipe));
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/lider/add/membro")
	public String addMembro(String email, Equipe equipe) {
		updateEquipe(service.addMembro(equipe, email));
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/lider/remove/membro")
	public String removeMembro(Voluntario voluntario, Equipe equipe) {
		updateEquipe(service.removeMembro(equipe, voluntario));
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/demanda/criar")
	public String createDemanda(Demanda demanda) {
		service.createDemanda(demanda, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@GetMapping("/demanda/capturar")
	public String capturaDemanda(Demanda demanda) {
		service.capturarDemanda(demanda, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@GetMapping("/demanda/descapturar")
	public String descapturaDemanda(Demanda demanda) {
		service.descapturarDemanda(demanda, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@GetMapping("/lider/descapturar")
	public String descapturaDemandaL(Demanda demanda) {
		service.descapturarDemanda(demanda);
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/lider/designar")
	public String designaDemanda(Demanda demanda, Voluntario voluntario) {
		service.capturarDemanda(demanda, voluntario);
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/demanda/add/nota")
	public String addNota(Nota nota) {
		service.createNotaComum(nota, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/demanda/add/pendencia")
	public String addPendencia(Nota nota) {
		service.addPendencia(nota, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/demanda/add/conclusao")
	public String addConclusao(Nota nota) {
		service.addConclusao(nota, info.getVoluntario());
		info.initDemandas();
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/demanda/add/resolucao")
	public String addResolucao(Nota nota) {
		service.addResolucao(nota, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/lider/add/reabertura")
	public String addReabertura(Nota nota) {
		service.addReabertura(nota, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@PostMapping("/lider/add/arquivamento")
	public String addArquivamento(Nota nota) {
		service.addArquivamento(nota, info.getVoluntario());
		return "redirect:/demanda/page";
	}
	
	@GetMapping("/demanda/notas")
	public String allNotas(Demanda demanda, Model model) {
		model.addAttribute("demanda", demanda);
		model.addAttribute("notas", service.allNotas(demanda));
		return "demandas/notas";
	}
}
