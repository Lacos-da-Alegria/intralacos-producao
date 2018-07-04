package com.lacosdaalegria.intralacos.controller.modules;

import javax.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.recurso.ControleNovato;
import com.lacosdaalegria.intralacos.model.recurso.Diretoria;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.service.HospitalService;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.RecursoService;
import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;
import com.lacosdaalegria.intralacos.session.InfoDiretoria;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
@RequiredArgsConstructor
public class RecursoController {

	private @NonNull RecursoService service;
	private @NonNull HospitalService hospital;
	private @NonNull UserInfo info;
	private @NonNull VoluntarioService vService;
	private @NonNull AtividadeService atividade;
	private @NonNull InfoDiretoria infoDiretoria;
	
	/*
	 * ======================================================================================
	 * =========================== Informações de Diretores =================================
	 * ======================================================================================
	 */
	
	@GetMapping("/info/contato")
	public String infoContato(Model model) {
		model.addAttribute("diretorias", service.allDiretorias());
		return "info/contato";
		
	}
	
	@GetMapping("/admin/create/role")
	public String infoContato(String role) {
		vService.createRole(role);
		return "redirect:/";
	}
	
	
	/*
	 * ======================================================================================
	 * ========================== Admin Controle de Diretores ===============================
	 * ======================================================================================
	 */
	 
	@GetMapping("/admin/diretoria")
	public String diretoriaPage(Model model) {
		model.addAttribute("diretorias", service.allDiretorias());
		return "admin/diretorias";
	}
	
	@PostMapping("/admin/cadastrar/diretoria")
	public String saveDiretoria(Diretoria diretoria) {
		service.saveDiretoria(diretoria);
		return "redirect:/admin/diretoria";
	}
	
	@PostMapping("/admin/cadastrar/diretor")
	public String saveDiretor(String email, Diretoria diretoria) {
		service.addDiretor(diretoria, email);
		return "redirect:/admin/diretoria";
	}
	
	@PostMapping("/admin/remover/diretor")
	public String removeDiretor(Diretoria diretoria, Voluntario voluntario) {
		service.removeDiretor(diretoria, voluntario);
		return "redirect:/admin/diretoria";
	}
	
	/*
	 * ======================================================================================
	 * ======================== Controle de Equipe de Demandas ==============================
	 * ======================================================================================
	 */ 
	
	@GetMapping("/diretor/cadastro/equipes")
	public String cadastroEquipe() {
		return "admin/cadastraEquipe";
	}
	
	@PostMapping("/diretor/cadastrar/equipe")
	public String removeDiretor(Equipe equipe, String email) {
		equipe.setDiretoria(service.findDiretoria(info.getVoluntario()));
		service.createEquipe(equipe, email);
		return "redirect:/diretor/cadastro/equipes";
	}
	
	@PostMapping("/lider/atualizar/equipe")
	public String updateEquipe(Equipe equipe, String email) {
		updateEquipeDiretor(service.updateEquipe(equipe, email));
		return "redirect:/demanda/page";
	}
	//Metodo que atualiza info da equipe atual selecionada pelo diretor
	//Deve ser tranferido para camada de serviço
	private void updateEquipeDiretor(Equipe equipe) {
		if(info.getVoluntario().hasRole("DIRETOR"))
			infoDiretoria.setEquipe(equipe);
	}
	
	/*
	 * ======================================================================================
	 * ================== Controle de Coordenadores e Equipe Novatos ========================
	 * ======================================================================================
	 */ 
	
	@GetMapping("/hospitais/recurso/humano")
	public String rhPage(Model model) {
		model.addAttribute("hospitais", this.hospital.getAll());
		model.addAttribute("coordenadores", service.activeCoordenadores());
		model.addAttribute("controladores", service.activeControleNovato());
		return "admin/recursoHumano";
	}
	
	@PostMapping("/hospitais/add/coordenador")
	public String addCoordenador(Hospital hospital, String email) {
		service.createCoordenador(hospital, email);
		return "redirect:/hospitais/recurso/humano";
	}
	
	@PostMapping("/hospitais/remove/coordenador")
	public String removeCoordenador(Voluntario voluntario) {
		service.removeCoordenador(voluntario);
		return "redirect:/hospitais/recurso/humano";
	}
	
	@PostMapping("/hospitais/add/controle/novato")
	public String addControleNovato(Hospital hospital, String email) {
		if(hospital.getNovatos())
			service.createControlador(hospital, email);
		return "redirect:/hospitais/recurso/humano";
	}
	
	@PostMapping("/hospitais/remove/controle/novato")
	public String removeControleNovato(Voluntario voluntario) {
		service.removeControlador(voluntario);
		return "redirect:/hospitais/recurso/humano";
	}
	
	/*
	 * ======================================================================================
	 * ============================== Controle de Novatos ===================================
	 * ======================================================================================
	 */ 
	
	@GetMapping("/controlador/page")
	public String controladorPage(Model model) {
		ControleNovato controle = service.findControlador(info.getVoluntario());
		model.addAttribute("controle", controle);
		model.addAttribute("novatos", hospital.top30Novatos(controle.getHospital()));
		model.addAttribute("inscritos", atividade.novatosInscrito(controle.getHospital()));
		return "novatos/controle";
	}
	
	@GetMapping("/controlador/inscrever/novato")
	public String inscreverNovato(Voluntario voluntario) {
		ControleNovato controle = service.findControlador(info.getVoluntario());
		atividade.inscreverNovato(voluntario, controle.getHospital());
		return "redirect:/controlador/page";
	}
	
	@GetMapping("/controlador/retirar/novato")
	public String retirarNovato(Voluntario voluntario) {
		ControleNovato controle = service.findControlador(info.getVoluntario());
		atividade.retirarNovato(voluntario, controle.getHospital());
		return "redirect:/controlador/page";
	}
	
	@GetMapping("/controlador/capturar/novato")
	public String capturaNovato(Voluntario voluntario) {
		vService.addResponsavel(voluntario, info.getVoluntario());
		return "redirect:/controlador/page";
	}
	
	@GetMapping("/controlador/descapturar/novato")
	public String descapturaNovato(Voluntario voluntario) {
		vService.removeReponsavel(voluntario);
		return "redirect:/controlador/page";
	}
	
	@PostMapping("/controlador/atualiza/observacao")
	public String atualizarObservacao(Voluntario voluntario, String observacao) {
		vService.atualizarObservacao(voluntario,info.getVoluntario(), observacao);
		return "redirect:/controlador/page";
	}
	
	@PostMapping("/controlador/desativar/novato")
	public String desativarNovato(Voluntario voluntario , String observacao) {
		vService.desativarNovato(voluntario, info.getVoluntario(), observacao);
		return "redirect:/controlador/page";
	}
		
	/*
	 * ======================================================================================
	 * ========================= Controle de Hospitais Diretoria ============================
	 * ======================================================================================
	 */ 
	
	@GetMapping("/hospitais/atualizar/hospital")
	public String updateHospital(Hospital hospital, Model model) {
		model.addAttribute("hospital", hospital);
		return "admin/updateHospital";
	}
	
	@PostMapping("/hospitais/atualizar/hospital")
	public String updateHospitalPost(Hospital hospital, Model model) {
		model.addAttribute("successMessage", "Informações do Hospital atualizadas com sucesso!");
		this.hospital.updateHospitalDiretoria(hospital);
		return "admin/updateHospital";
	}
	
	@GetMapping("/hospitais/add/hospital")
	public String addHospital(Model model) {
		model.addAttribute("hospital", new Hospital());
		model.addAttribute("novo", true);
		return "admin/updateHospital";
	}
	
	@PostMapping("/hospitais/add/hospital")
	public ModelAndView updateInfo(@Valid Hospital hospital, BindingResult result) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("admin/updateHospital");
		
    	if(!result.hasErrors()) {
    		modelAndView.addObject("hospital", this.hospital.saveHospital(hospital));
            modelAndView.addObject("successMessage", "Hospital cadastrado com sucesso!");
            
    	} else {
    		modelAndView.addObject("errorMessage", "Erro nas informações de cadastro");
    		modelAndView.addObject("hospital", hospital);
    		modelAndView.addObject("novo", true);
    	}
    	
    	return modelAndView;
	}
	
	@PostMapping("/hospitais/imagem/hospital")
    public String updateProfile(MultipartFile file, Hospital hospital, Model model) {
		this.hospital.updateImage(file, hospital);
		model.addAttribute("successMessage", "Imagem do Hospital atualizada com sucesso!");
		return "admin/updateHospital";	
	}
	
}
