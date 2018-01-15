package com.lacosdaalegria.intralacos.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.service.HospitalService;
import com.lacosdaalegria.intralacos.service.RegiaoService;
import com.lacosdaalegria.intralacos.service.VoluntarioService;

@Controller
public class UserController {

	@Autowired
	HospitalService hospital;
	@Autowired
	RegiaoService regiao;
	@Autowired
	VoluntarioService service;
	
	@GetMapping("/cadastro")
	public String registerPage(Model model) {
		
		model.addAttribute("voluntario", new Voluntario());
		
		model.addAttribute("hospitais", hospital.getAllActive());
		model.addAttribute("ras", regiao.getAllActive());
		
		return "register";
	}
	
    @PostMapping("/cadastro")
    public ModelAndView createNewUser(@Valid Voluntario voluntario, BindingResult result, 
    				HttpServletRequest request) {
    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("voluntario", voluntario);
    	
        service.duplicidadeInfo(voluntario, result);
    	
    	if(result.hasErrors()) {
    		
    		modelAndView.addObject("hospitais", hospital.getAllActive());
    		modelAndView.addObject("ras", regiao.getAllActive());

    		modelAndView.setViewName("register");
    		
    	} else {
    		
    		service.registerVoluntario(voluntario);
        	
            modelAndView.addObject("successMessage", "Você foi cadastrado com sucesso!");
            
            modelAndView.setViewName("login");
    	}
    	
    	return modelAndView;
    }
	
}
