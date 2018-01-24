package com.lacosdaalegria.intralacos.controller.user;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lacosdaalegria.intralacos.email.EmailService;
import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.model.ResetToken;
import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.service.HospitalService;
import com.lacosdaalegria.intralacos.service.RegiaoService;
import com.lacosdaalegria.intralacos.service.VoluntarioService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class VoluntarioController {
	
	@Autowired
	private RegiaoService regiao;
	@Autowired
	private UserInfo info;
	@Autowired
	private VoluntarioService service;
	@Autowired
	private HospitalService hospital; 
	@Autowired
	private OngsService ongs;
	@Autowired
	private EmailService email;
	
	@GetMapping("/info/usuario")
	public String userPage(Model model) {
		model.addAttribute("voluntario", info.getVoluntario());
		model.addAttribute("ras", regiao.getAllActive());
		
		return "userPage";
	}
	
	@PostMapping("/info/atualizar/")
	public ModelAndView updateInfo(@Valid Voluntario voluntario, BindingResult result) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("ras", regiao.getAllActive());
		modelAndView.setViewName("userPage");
		
    	if(hasNoErroUpdate(result)) {
    		
    		service.updateUserInfo(info.getVoluntario(), voluntario);
    		modelAndView.addObject("voluntario", info.getVoluntario());
            modelAndView.addObject("successMessage", "Informações atualizadas com sucesso!");
            
    	}
    	
    	return modelAndView;
	}
	
	@PostMapping("/info/atualizar/profile")
    public String updateProfile(MultipartFile file) {
		
		service.updateProfile(file, info.getVoluntario());
		
        return "redirect:/info/usuario";
    }
	
	private boolean hasNoErroUpdate(BindingResult result) {
		
		for(ObjectError e : result.getAllErrors()) {
			if(!e.getCodes()[0].contains("senha") && !e.getCodes()[0].contains("preferencia"))
				return false;
		}
	
		return true;
		
	}
	
	@GetMapping("/voluntario/home")
	public String voluntarioPage(Model model) {
		model.addAttribute("codigo", Global.getCodigo());
		model.addAttribute("hospitais", hospital.getAllActive());
		model.addAttribute("acoes", ongs.getAcoesAtivas());
		model.addAttribute("rodada", Global.rodadaRandomica());
		return "home";
	}
	
	@GetMapping("/cadastro/gerar/token")
	public String resetToken(String email, RedirectAttributes redirectAttrs) {
		Voluntario voluntario = service.findByEmail(email);
		if(voluntario != null) {
			redirectAttrs.addFlashAttribute("successMessage", "E-mail enviado para resetar sua senha");
			ResetToken token = service.createToken(voluntario);
			this.email.sendToken(token);
		} else {
			redirectAttrs.addFlashAttribute("errorMessage", "Não há cadastro vinculado a esse e-mail");
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/cadastro/reset/senha")
	public String resetSenha(String token, String login, Model model) {
		model.addAttribute("login", login);
		model.addAttribute("token", token);
		return "resetSenha";
	}
	
	@PostMapping("/cadastro/resetar/senha")
	public String resetarSenha(String token, String senha, String _senha, RedirectAttributes redirectAttrs) {
		String mensagem = service.resetSenha(token, senha, _senha);
		if(mensagem.contains("@")) {
			redirectAttrs.addFlashAttribute("errorMessage", mensagem.replace("@", ""));
		} else {
			redirectAttrs.addFlashAttribute("successMessage", mensagem);
		}
		return "redirect:/login";
	}
	
	
	/*
	 * ======================================================================================
	 * =================================== NOVATOS ==========================================
	 * ======================================================================================
	 */

	
	@GetMapping("/novato/home")
	public String home(Model model) {
		model.addAttribute("hospitais", hospital.getAllActive());
		return "novatos/home";
	}
	
	@PostMapping("/novato/atualizar/preferencia")
	public String updatePreferencia(Hospital hospital) {
		service.updatePreferencia(info.getVoluntario(), hospital);
		info.resetPosicao();
		return "redirect:/novato/home";
	}
	
	@PostMapping("/novato/promover")
	public String promoveNovato(String codigo){
		if(Objects.equals(Global.getCodigo(), codigo)) {
			service.promoteNovato(info.getVoluntario());
			return "redirect:/logout";
		}
		return "redirect:/";
	}
	
	@GetMapping("/novato/reativar/conta")
	public String activeAcount() {
		service.reativarConta(info.getVoluntario());
		return "redirect:/novato/home";
	}
}
