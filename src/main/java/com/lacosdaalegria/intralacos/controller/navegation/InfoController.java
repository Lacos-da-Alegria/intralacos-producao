package com.lacosdaalegria.intralacos.controller.navegation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lacosdaalegria.intralacos.service.VoluntarioService;

@Controller
public class InfoController {
	
	@Autowired
	private VoluntarioService service;

	@GetMapping("/info/faq")
	public String infoFaq() {
		return "info/faq";
	}
	
	@GetMapping("/info/termo")
	public String infoTermo() {
		return "info/termo_leitura";
	}
	
	@GetMapping("/info/historia")
	public String infoHistoria() {
		return "info/historia";
	}
	
	@GetMapping("/info/ajudar")
	public String infoAjudar() {
		return "info/ajudar";
	}
	
	@GetMapping("/info/aniversariantes")
	public String infoAniver(Model model) {
		model.addAttribute("aniversariantes", service.aniversariantes());
		return "info/aniversariantes";
	}
	
	@GetMapping("/voluntario/calendario/acoes")
	public String infoCalendarioAcoes() {
		System.out.println("Batata");
		return "info/calendario";
	}
	
}
