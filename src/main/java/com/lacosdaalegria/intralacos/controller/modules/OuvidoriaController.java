package com.lacosdaalegria.intralacos.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ouvidoria.Atendimento;
import com.lacosdaalegria.intralacos.model.ouvidoria.Categoria;
import com.lacosdaalegria.intralacos.model.ouvidoria.Feedback;
import com.lacosdaalegria.intralacos.model.ouvidoria.Grupo;
import com.lacosdaalegria.intralacos.service.VoluntarioService;
import com.lacosdaalegria.intralacos.service.modules.OuvidoriaService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class OuvidoriaController {
	
	@Autowired
	private OuvidoriaService service;
	@Autowired
	private VoluntarioService vService;
	@Autowired
	private UserInfo info;
	
	/*
	 * ======================================================================================
	 * =================================== Navegação ========================================
	 * ======================================================================================
	 */

	@GetMapping("/info/ouvidoria")
	public String homePage(Model model) {
		model.addAttribute("categorias", service.categoriasAtivas());
		model.addAttribute("atendimentos", service.findMyAtendimentos(info.getVoluntario()));
		return "atendimento/ouvidoria";
	}
	
	@GetMapping("/comunicacao/ouvidoria")
	public String adminPage(Model model) {
		
		model.addAttribute("grupos", service.allGrupos());
		model.addAttribute("categorias", service.categoriasAtivas());
		
		return "atendimento/controle";
	}
	
	@GetMapping("/atendimento/page")
	public String atendimentoPage(Model model) {
		
		Grupo grupo = service.findGrupo(info.getVoluntario());
		
		model.addAttribute("grupo", grupo);
		model.addAttribute("atendimentos", service.findAtendimentos(grupo));
		
		return "atendimento/home";
	}
	
	/*
	 * ======================================================================================
	 * ============================== Controle Ouvidoria ====================================
	 * ======================================================================================
	 */
	
	@PostMapping("/comunicacao/cadastrar/grupo")
	public String saveGrupo(Grupo grupo){
		service.saveGrupo(grupo);
		return "redirect:/comunicacao/ouvidoria";
	}
	
	@PostMapping("/comunicacao/cadastrar/categoria")
	public String saveCategoria(Categoria categoria) {
		service.saveCategoira(categoria);
		return "redirect:/comunicacao/ouvidoria";
	}
	
	@PostMapping("/comunicacao/retirar/categoria")
	public String removeCategoria(Categoria categoria) {
		service.retirarCategoria(categoria);
		return "redirect:/comunicacao/ouvidoria";
	}
	
	@PostMapping("/comunicacao/adicionar/categoria")
	public String addCategoria(Grupo grupo, Categoria categoria) {
		service.addCategoria(grupo, categoria);
		return "redirect:/comunicacao/ouvidoria";
	}
	
	@PostMapping("/comunicacao/adicionar/atendente")
	public String addAtendente(Grupo grupo, String email) {
		Voluntario voluntario = vService.addRole(email, "ROLE_ATEND");
		service.addAtendente(grupo, voluntario);
		return "redirect:/comunicacao/ouvidoria";
	}
	
	@PostMapping("/comunicacao/retirar/atendente")
	public String removeAtendente(Grupo grupo, Voluntario voluntario) {
		vService.removeRole(voluntario, "ROLE_ATEND");
		service.removeAtendente(voluntario, grupo);
		return "redirect:/comunicacao/ouvidoria";
	}
	
	/*
	 * ======================================================================================
	 * =============================== Página Para Uso ======================================
	 * ======================================================================================
	 */
	
	@PostMapping("/info/criar/atendimento")
	public String createAtendimento(Atendimento atendimento) {
		service.saveAtendimento(atendimento, info.getVoluntario());
		return "redirect:/info/ouvidoria";
	}
	
	@PostMapping("/info/criar/feedback")
	public String createFeedback(Feedback feedback) {
		service.saveFeedback(feedback);
		return "redirect:/info/ouvidoria";
	}
	
	@GetMapping("/info/atendimento/concluir")
	public String concluiAtendimento(Atendimento atendimento) {
		service.concluiAtendimento(atendimento);
		info.reduzAtendimento();
		return "redirect:/info/ouvidoria";
	}
	
	/*
	 * ======================================================================================
	 * =========================== Página Para Atendimento ==================================
	 * ======================================================================================
	 */
	
	@GetMapping("/atendimento/capturar")
	public String capturaAtendimento(Atendimento atendimento) {
		service.capturaAtendimento(atendimento, info.getVoluntario());	
		return "redirect:/atendimento/page";
	}
	
	@GetMapping("/atendimento/descapturar")
	public String descapturaAtendimento(Atendimento atendimento) {
		service.descapturaAtendimento(atendimento, info.getVoluntario());	
		return "redirect:/atendimento/page";
	}
	
	@PostMapping("/atendimento/reponder")
	public String repondeAtendimento(Atendimento atendimento) {
		service.respondeAtendimento(atendimento, info.getVoluntario());	
		return "redirect:/atendimento/page";
	}
}
