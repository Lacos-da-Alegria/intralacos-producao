package com.lacosdaalegria.intralacos.controller.navegation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InfoController {
	
	private @NonNull VoluntarioService service;

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
		return "info/calendario";
	}
	
}
