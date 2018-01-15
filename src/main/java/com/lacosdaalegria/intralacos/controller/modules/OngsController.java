package com.lacosdaalegria.intralacos.controller.modules;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lacosdaalegria.intralacos.model.Regiao;
import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.ongs.Instituicao;
import com.lacosdaalegria.intralacos.model.ongs.Polo;
import com.lacosdaalegria.intralacos.model.ongs.Tag;
import com.lacosdaalegria.intralacos.service.RegiaoService;
import com.lacosdaalegria.intralacos.service.VoluntarioService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Controller
public class OngsController {
	
	@Autowired
	private OngsService service;
	@Autowired
	private UserInfo info;
	@Autowired
	private RegiaoService regiao;
	@Autowired
	private VoluntarioService vService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@GetMapping("/polo/instituicoes")
	public String instituicoes(Model model){
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("instituicoes", service.findInstituicoes(polo));
		return "ongs/instituicoes";
	}
	
	@GetMapping("/ongs/tags")
	public String controleTags(Model model) {
		model.addAttribute("tags", service.allTags());
		return "ongs/tags";
	}
	
	@GetMapping("/polo/calendario")
	public String calendario(Model model){
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("instituicoes", service.findInstituicoes(polo));
		return "ongs/calendario";
	}
	
	@GetMapping("/polo/detalhe/instituicao")
	public String detalheInstituicao(Instituicao instituicao, Model model) {
		model.addAttribute("instituicao", instituicao);
		return "ongs/detalheInstituicao";
	}
	
	@GetMapping("/ongs/equipes")
	public String ongsControle(Model model) {
		model.addAttribute("regioes", regiao.semPolo());
		model.addAttribute("polos", service.allPolos());
		return "ongs/equipeOngs";
	}
	
	@GetMapping("/polo/cadatro/instituicao")
	public String cadastroInstituicao(Model model) {
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("regioes", regiao.poloRegioes(polo));
		model.addAttribute("tags", service.activeTags());
		return "ongs/cadastroInstituicoes";
	}
	
	@PostMapping("/ongs/cadastrar/polo")
	public String cadastraPolo(Polo polo) {
		service.addPolo(polo);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/polo/cadastrar/instituicao")
	public String cadastraInstituicao(Instituicao instituicao) {
		service.saveInstituicao(instituicao);
		return "redirect:/polo/instituicoes";
	}
	
	@PostMapping("/ongs/adicionar/membro")
	public String addMembro(Polo polo, String email) {
		Voluntario voluntario = vService.addRole(email, "ROLE_ONGS");
		service.addMembro(polo, voluntario);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/remove/membro")
	public String removeMembro(Voluntario voluntario) {
		vService.removeRole(voluntario, "ROLE_ONGS");
		service.removeMembro(voluntario);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/remove/regiao")
	public String removeRegiao(Regiao regiao) {
		service.removeRegiao(regiao);
		return "redirect:/ongs/equipes";
	}
	
	
	@PostMapping("/ongs/add/regiao")
	public String addRegiao(Polo polo) {
		service.addRegioes(polo);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/add/tag")
	public String addRegiao(Tag tag) {
		service.addTag(tag);
		return "redirect:/ongs/tags";
	}
	
	@GetMapping("/ongs/update/tag")
	public String alteraStatusTag(Tag tag) {
		service.updateTag(tag);
		return "redirect:/ongs/tags";
	}
	
	@PostMapping("/polo/agendar/acao")
	public String agendaAcao(Agenda agenda) {
		service.agendaAcao(agenda, info.getVoluntario());
		return "redirect:/polo/calendario";
	}
	
	@PostMapping("/polo/cancelar/acao")
	public String cancelarAcao(Agenda agenda) {
		service.cancelarAcao(agenda);
		return "redirect:/polo/calendario";
	}
	
	@GetMapping("/polo/get/agendas")
	public @ResponseBody Iterable<Agenda> agendasPolo(Polo polo){
		return service.getAgenda(polo);
	}
	
	@GetMapping("/polo/pesquisa/agenda")
	public @ResponseBody Agenda agendaPolo(Agenda agenda){
		return agenda;
	}
	
	@PostMapping("/polo/update/imagem")
    public String updateProfile(MultipartFile file, Instituicao instituicao) {
		service.updateImage(file, instituicao);
		return "redirect:/polo/detalhe/instituicao?instituicao="+instituicao.getId();	
	}
}
