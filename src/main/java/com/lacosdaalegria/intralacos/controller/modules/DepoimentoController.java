package com.lacosdaalegria.intralacos.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lacosdaalegria.intralacos.model.social.Depoimento;
import com.lacosdaalegria.intralacos.service.modules.SocialService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@RestController
public class DepoimentoController {
	
	@Autowired
	private SocialService service;
	@Autowired
	private UserInfo info;
	
	@PostMapping("/voluntario/add/depoimento")
	public Depoimento addDepoimento(Depoimento depoimento) {
		return service.saveDepoimento(depoimento, info.getVoluntario());
	}
	
	@GetMapping("/info/get/depoimentos")
	public Page<Depoimento> getDepoimentos(Integer page) {
		return service.pageDepoimento(page);
	}
	
	@GetMapping("/diretor/delete/depoimento")
	public void deleteDepoimento(Depoimento depoimento) {
		service.deleteDepoimento(depoimento);
	}

}
