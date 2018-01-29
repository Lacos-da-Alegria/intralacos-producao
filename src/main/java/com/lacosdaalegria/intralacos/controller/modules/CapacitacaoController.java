package com.lacosdaalegria.intralacos.controller.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lacosdaalegria.intralacos.model.capacitacao.Aula;
import com.lacosdaalegria.intralacos.model.capacitacao.Educador;
import com.lacosdaalegria.intralacos.model.capacitacao.Materia;
import com.lacosdaalegria.intralacos.service.modules.CapacitacaoService;

@Controller
public class CapacitacaoController {

	@Autowired
	private CapacitacaoService service;
	
	/*
	 * ======================================================================================
	 * ======================== Controle Equipe de Capacitação ==============================
	 * ======================================================================================
	 */
	 
	@GetMapping("/executivo/controle/educadores")
	public String controleEducadores(Model model) {
		model.addAttribute("educadores", service.allEducadores());
		return "capacitacao/equipe";
	}
	
	@PostMapping("/executivo/add/educador")
	public String addAnalista(String email) {
		service.addEducador(email);
		return "redirect:/executivo/controle/educadores";
	}
	
	@PostMapping("/executivo/remove/educador")
	public String removaEducador(Educador educador) {
		service.removaEducador(educador);
		return "redirect:/executivo/controle/educadores";
	}
	
	/*
	 * ======================================================================================
	 * ============================= Controle de Materias ===================================
	 * ======================================================================================
	 */
	
	@GetMapping("/capacitacao/controle/materias")
	public String controleMateriais(Model model) {
		model.addAttribute("materias", service.allMaterias());
		return "capacitacao/controle-materias";
	}
	
	@PostMapping("/capacitacao/create/materia")
	public String createMateria(Materia materia) {
		service.createMateria(materia);
		return "redirect:/capacitacao/controle/materias";
	}
	
	@GetMapping("/capacitacao/status/materia")
	public String desativaMateria(Materia materia) {
		service.atualizaStatus(materia);
		return "redirect:/capacitacao/controle/materias";
	}
	
	@PostMapping("/capacitacao/imagem/materia")
    public String updateImagemMateria(MultipartFile file, Materia materia, RedirectAttributes redirectAttrs) {
		service.updateImage(file, materia);
		redirectAttrs.addFlashAttribute("successMessage", "Imagem da Materia atualizada com sucesso!");
		return "redirect:/capacitacao/controle/materias";
	}
	
	/*
	 * ======================================================================================
	 * =============================== Controle de Aulas ====================================
	 * ======================================================================================
	 */
	
	@GetMapping("/capacitacao/controle/aulas")
	public String controleAulas(Materia materia, Model model) {
		model.addAttribute("materia", materia);
		model.addAttribute("aulas", service.aulasMateria(materia));
		return "capacitacao/controle-aulas";
	}
	
	@PostMapping("/capacitacao/create/aula")
	public String createAula(Aula aula) {
		service.createAula(aula);
		return "redirect:/capacitacao/controle/aulas?materia="+aula.getMateria().getId();
	}
	
	@PostMapping("/capacitacao/imagem/aula")
    public String updateImagemAula(MultipartFile file, Aula aula, RedirectAttributes redirectAttrs) {
		service.updateImage(file, aula);
		redirectAttrs.addFlashAttribute("successMessage", "Imagem da Aula atualizada com sucesso!");
		return "redirect:/capacitacao/controle/aulas?materia="+aula.getMateria().getId();
	}
	
	@GetMapping("/capacitacao/status/aula")
	public String statusAula(Aula aula) {
		service.atualizaStatus(aula);
		return "redirect:/capacitacao/controle/aulas?materia="+aula.getMateria().getId();
	}
	
	/*
	 * ======================================================================================
	 * =================================== Navegação ========================================
	 * ======================================================================================
	 */
	
	@GetMapping("/info/capacitacao")
	public String capacitacaoPage(Model model) {
		model.addAttribute("materias", service.materiasAtivas());
		return "capacitacao/materias";
	}
	
	@GetMapping("/info/aulas")
	public String capacitacaoPage(Materia materia, Model model) {
		model.addAttribute("materia", materia);
		model.addAttribute("aulas", service.aulasAtivas(materia));
		return "capacitacao/aulas";
	}
	
}
