package com.lacosdaalegria.intralacos.controller.navegation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class GeneralController {
	
	@Autowired
	UserInfo info;
	
	@GetMapping("/login")
	public String logginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String initial() {
		
		System.out.println(Global.getCodigo());
		
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
