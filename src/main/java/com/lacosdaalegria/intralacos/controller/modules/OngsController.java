package com.lacosdaalegria.intralacos.controller.modules;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.model.atividade.Fila;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.ongs.Instituicao;
import com.lacosdaalegria.intralacos.model.ongs.Polo;
import com.lacosdaalegria.intralacos.model.ongs.Tag;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.model.usuario.RoleEnum;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.service.RegiaoService;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;
import com.lacosdaalegria.intralacos.session.UserInfo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OngsController {
	
	private @NonNull OngsService service;
	private @NonNull UserInfo info;
	private @NonNull RegiaoService regiao;
	private @NonNull VoluntarioService vService;
	private @NonNull AtividadeService atividade;
	
	/**
	* Metodo que inicializa bind de formato de datas para o objeto Date
	*/
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/*
	 * ======================================================================================
	 * ============================ Controle de Equipe Polos ================================
	 * ======================================================================================
	 */ 
	 
	@GetMapping("/ongs/equipes")
	public String ongsControle(Model model) {
		model.addAttribute("regioes", regiao.semPolo());
		model.addAttribute("polos", service.allPolos());
		return "ongs/equipeOngs";
	}
	
	@PostMapping("/ongs/cadastrar/polo")
	public String cadastraPolo(Polo polo) {
		service.addPolo(polo);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/add/regiao")
	public String addRegiao(Polo polo) {
		service.addRegioes(polo);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/adicionar/membro")
	public String addMembro(Polo polo, String email) {
		Voluntario voluntario = vService.addRole(email, RoleEnum.POLO);
		service.addMembro(polo, voluntario);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/remove/membro")
	public String removeMembro(Voluntario voluntario) {
		service.removeMembro(voluntario);
		return "redirect:/ongs/equipes";
	}
	
	@PostMapping("/ongs/remove/regiao")
	public String removeRegiao(Regiao regiao) {
		service.removeRegiao(regiao);
		return "redirect:/ongs/equipes";
	}
	
	/*
	 * ======================================================================================
	 * ============================ Controle de Tags ================================
	 * ======================================================================================
	 */ 
	 
	@GetMapping("/ongs/tags")
	public String controleTags(Model model) {
		model.addAttribute("tags", service.allTags());
		return "ongs/tags";
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
	
	/*
	 * ======================================================================================
	 * ============================ Controle de Chamadas ================================
	 * ======================================================================================
	 */ 
	 
	 
	@GetMapping("/polo/chamadas")
	public String chamadas(Agenda agenda, Model model){
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("agendas", service.getChamadas(polo));
		if(agenda.getId() != null) {
			//Verifica se instituição é do mesmo polo
			if(agenda.getInstituicao().getPolo().getId().equals(polo.getId())) {
				model.addAttribute("fila", atividade.getFilaAtividade(agenda));
				model.addAttribute("agenda", agenda);
			}
		}
		return "ongs/chamada";
	}
	
	@GetMapping("/polo/finalizar/chamada")
	public String voluntarioPresente(Agenda agenda, RedirectAttributes redirectAttrs) {
		if(atividade.finalizaChamada(agenda)) {
			redirectAttrs.addFlashAttribute("successMessage", "Chamada finalizada com sucesso");
			return "redirect:/polo/chamadas";
		} else {
			redirectAttrs.addFlashAttribute("erroMessage", "Ainda há voluntários na chamada");
			return "redirect:/polo/chamadas?agenda="+agenda.getId();
		}
	}
	
	@GetMapping("/polo/lista/atividade")
	public String listaAtividade(Model model) {
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("acoes", service.getParticipantes(polo));
		model.addAttribute("rodada", Global.rodadaRandomica());
		model.addAttribute("fila", new Fila());
		return "ongs/lista";
	}
	
	@GetMapping("/polo/lista/acao")
	public String listaAcao(Agenda agenda, Model model) {
		
		if(agenda == null){
			return "redirect:/polo/lista/atividade";
		}
		
		Polo polo = service.myPolo(info.getVoluntario());
		
		if(agenda.getId() != null) {
			//Verifica se instituição é do mesmo polo
			if(agenda.getInstituicao().getPolo().getId().equals(polo.getId())) {
				
				model.addAttribute("rodada", Global.rodadaRandomica());
				model.addAttribute("acoes", service.getParticipantes(polo));
				model.addAttribute("fila", atividade.getFilaAtividade(agenda));
				model.addAttribute("agenda", agenda);
				
				return "ongs/lista";
			}
		}
		
		return "redirect:/polo/lista/atividade";
	}
	

	/*
	 * ======================================================================================
	 * ========================= Calendario de Ações / Agendas ==============================
	 * ======================================================================================
	 */
	 
	@GetMapping("/polo/calendario")
	public String calendario(Model model){
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("instituicoes", service.findInstituicoes(polo));
		return "ongs/calendario";
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
	 
	/*
	 * ======================================================================================
	 * ============================ Controle de Instituições ================================
	 * ======================================================================================
	 */
	
	@GetMapping("/polo/instituicoes")
	public String instituicoes(Model model){
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("instituicoes", service.findInstituicoes(polo));
		return "ongs/instituicoes";
	}
	
	@GetMapping("/polo/detalhe/instituicao")
	public String detalheInstituicao(Instituicao instituicao, Model model) {
		model.addAttribute("instituicao", instituicao);
		return "ongs/detalheInstituicao";
	}
	
	@GetMapping("/polo/cadatro/instituicao")
	public String cadastroInstituicao(Model model) {
		
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("regioes", regiao.poloRegioes(polo));
		model.addAttribute("tags", service.activeTags());
		model.addAttribute("instituicao", new Instituicao());
		
		return "ongs/cadastroInstituicoes";
	}
	
	@GetMapping("/polo/atualizar/instituicao")
	public String cadastroInstituicao(Instituicao instituicao, Model model) {
		
		Polo polo = service.myPolo(info.getVoluntario());
		model.addAttribute("polo", polo);
		model.addAttribute("regioes", regiao.poloRegioes(polo));
		model.addAttribute("tags", service.activeTags());
		model.addAttribute("instituicao", instituicao);
		
		return "ongs/cadastroInstituicoes";
	}
	
	@PostMapping("/polo/cadastrar/instituicao")
	public String cadastraInstituicao(@Valid Instituicao instituicao, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		
		Polo polo = service.myPolo(info.getVoluntario());
		
		if(result.hasErrors()) {
			
			model.addAttribute("polo", polo);
			model.addAttribute("regioes", regiao.poloRegioes(polo));
			model.addAttribute("tags", service.activeTags());
			
			model.addAttribute("instituicao", instituicao);
			
			return "ongs/cadastroInstituicoes";
			
		} else {
			
			redirectAttrs.addFlashAttribute("successMessage", "Instituição salva com sucesso!");
			
			instituicao.setPolo(polo);
			
			service.saveInstituicao(instituicao);
			
			return "redirect:/polo/instituicoes";
		}
		
		
	}
	
	@PostMapping("/polo/update/imagem")
    public String updateProfile(MultipartFile file, Instituicao instituicao) {
		service.updateImage(file, instituicao);
		return "redirect:/polo/detalhe/instituicao?instituicao="+instituicao.getId();	
	}
	
	/*
	 * ======================================================================================
	 * =========================== Calendario Aberto de Ações ===============================
	 * ======================================================================================
	 */ 
	
	@GetMapping("/voluntario/get/acoes")
	public @ResponseBody Iterable<Agenda> calendarioAcoes(){
		return service.calendarioAcoes();
	}
	
	@GetMapping("/voluntario/pesquisa/agenda")
	public @ResponseBody Agenda agendaVoluntario(Agenda agenda){
		return agenda;
	}
}
