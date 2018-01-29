package com.lacosdaalegria.intralacos.controller.modules;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lacosdaalegria.intralacos.model.social.Carona;
import com.lacosdaalegria.intralacos.model.social.Depoimento;
import com.lacosdaalegria.intralacos.service.HospitalService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.service.modules.SocialService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class SocialController {
	
	@Autowired
	private SocialService service;
	@Autowired
	private HospitalService hospital;
	@Autowired
	private OngsService ongs;
	@Autowired
	private UserInfo info;
	
	/*
	 * ======================================================================================
	 * ================================ Página de Depoimentos ===============================
	 * ======================================================================================
	 */
	
	@GetMapping("/info/depoimentos")
	public String infoDepoimentos(HttpServletRequest request, Model model) {
		//Habilitar botão para exclusão logica de depoimento
		if(request.isUserInRole("DIRETOR"))
			model.addAttribute("diretor", "c230c8dc-0d8e-4721-94ac-5291ad330be6");
		return "info/depoimentos";
	}
	
	@PostMapping("/voluntario/add/depoimento")
	public @ResponseBody Depoimento addDepoimento(Depoimento depoimento) {
		return service.saveDepoimento(depoimento, info.getVoluntario());
	}
	
	@GetMapping("/info/get/depoimentos")
	public @ResponseBody Page<Depoimento> getDepoimentos(Integer page) {
		return service.pageDepoimento(page);
	}
	
	@GetMapping("/diretor/delete/depoimento")
	public void deleteDepoimento(Depoimento depoimento) {
		service.deleteDepoimento(depoimento);
	}
	
	/*
	 * ======================================================================================
	 * ================================== Pagina de Caronas =================================
	 * ======================================================================================
	 */
	
	@GetMapping("/voluntario/caronas")
	public String caronasPage(Model model) {
		model.addAttribute("hospitais", hospital.getAllActive());
		model.addAttribute("acoes", ongs.getAcoesAtivas());
		return "social/caronas";
	}
	
	@PostMapping("/voluntario/create/carona")
	public @ResponseBody Carona createCarona(Carona carona) {
		return service.createCarona(carona, info);
	}
	
	@GetMapping("/voluntario/get/caronas")
	public @ResponseBody Page<Carona> getCaronas(Integer page) {
		return service.pagecarona(page);
	}
	
	@GetMapping("/voluntario/quero/carona")
	public @ResponseBody Carona queroCaronaa(Carona carona) {
		return service.queroCarona(carona, info);
	}
	
	@GetMapping("/voluntario/cancelar/carona")
	public @ResponseBody boolean cancelarCaronaa(Carona carona) {
		return service.cacelarCarona(carona, info);
	}

}
