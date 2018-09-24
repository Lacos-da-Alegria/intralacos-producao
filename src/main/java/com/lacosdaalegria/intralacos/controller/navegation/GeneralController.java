package com.lacosdaalegria.intralacos.controller.navegation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lacosdaalegria.intralacos.session.UserInfo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GeneralController {
	
	private @NonNull UserInfo info;
	
	@GetMapping("/login")
	public String logginPage() {
		return "login";
	}

	/**
	* Lógica de triagem para pagina inicial que deve ser apresentada no momento do acesso
	*/
	@GetMapping("/")
	public String initial() {
		if(info.hasRole("ROLE_ACEITE")) {
			return "redirect:/termo/aceite";
		} else {
			if(info.hasRole("ROLE_NOVATO")) {
				return "redirect:/novato/home";
			} else {
				return "redirect:/voluntario/home";
			}
		}
	}

}
