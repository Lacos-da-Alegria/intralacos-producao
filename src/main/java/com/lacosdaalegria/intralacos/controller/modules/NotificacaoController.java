package com.lacosdaalegria.intralacos.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.lacosdaalegria.intralacos.service.modules.OuvidoriaService;
import com.lacosdaalegria.intralacos.session.UserInfo;

/**
*	Controladora que disponibiliza em todas views a informação se existem novos atendimentos abertos para o devido grupo
*/
@ControllerAdvice
public class NotificacaoController {
	
	@Autowired
	private UserInfo info;
	@Autowired
	private OuvidoriaService ouvidoria;
	
	@ModelAttribute
	public void addAttributes(Model model) {
		if(info.getVoluntario() != null) {
			if(info.getVoluntario().hasRole("ATEND"))
				model.addAttribute("chamadosAbertos", ouvidoria.ouvidoriasAbertas(info.getVoluntario()));
		}
	}
}
