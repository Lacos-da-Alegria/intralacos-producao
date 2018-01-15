package com.lacosdaalegria.intralacos.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lacosdaalegria.intralacos.service.VoluntarioService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class TermoController {
	
	@Autowired
	VoluntarioService service;
	@Autowired
	UserInfo info;
	
	@GetMapping("/termo/aceite")
	public String termoPage() {
		return "info/termo";
	}
	
	@GetMapping("/termo/aceitar")
	public String registerPage(boolean aceite, HttpSession session) {
		if(aceite) {
			service.aceitaTermo(info.getVoluntario());
			session.setAttribute("vindas", true);
			return "redirect:/novato/home";
		} else 
			return "redirect:/termo/aceite";
	}
	
}
