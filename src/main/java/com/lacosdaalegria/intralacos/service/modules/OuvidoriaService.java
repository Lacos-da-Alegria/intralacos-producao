package com.lacosdaalegria.intralacos.service.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.ouvidoria.Atendimento;
import com.lacosdaalegria.intralacos.model.ouvidoria.Categoria;
import com.lacosdaalegria.intralacos.model.ouvidoria.Feedback;
import com.lacosdaalegria.intralacos.model.ouvidoria.Grupo;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.ouvidoria.AtendimentoRepository;
import com.lacosdaalegria.intralacos.repository.ouvidoria.CategoriaRepository;
import com.lacosdaalegria.intralacos.repository.ouvidoria.FeedbackRepository;
import com.lacosdaalegria.intralacos.repository.ouvidoria.GrupoRepository;

@Service
public class OuvidoriaService {
	
	@Autowired
	private AtendimentoRepository atendimento;
	@Autowired
	private CategoriaRepository categoria;
	@Autowired
	private GrupoRepository grupo;
	@Autowired
	private FeedbackRepository feedback;
	
	public void saveCategoira(Categoria categoria){
		this.categoria.save(categoria);
	}
	
	public void saveGrupo(Grupo grupo) {
		this.grupo.save(grupo);
	}
	
	public Iterable<Categoria> categoriasAtivas(){
		return categoria.findByStatus(1);
	}
	
	public Iterable<Grupo> allGrupos(){
		return grupo.findAll();
	}
	
	public Page<Atendimento> pageAtendimento(Integer page){
		
		if(page == null)
			page = 0;
		else
			page = page - 1;
		
		return this.atendimento.findAllByOrderByCriacaoDesc(PageRequest.of(page, 12));
	}
	
	public void retirarCategoria(Categoria categoria) {
		categoria.setGrupo(null);
		this.categoria.save(categoria);
	}
	
	public void addCategoria(Grupo grupo, Categoria categoria) {
		categoria.setGrupo(grupo);
		this.categoria.save(categoria);
	}
	
	public void addAtendente(Grupo grupo, Voluntario voluntario) {
		grupo.getAtendentes().add(voluntario);
		this.grupo.save(grupo);
	}
	
	public void removeAtendente(Voluntario voluntario, Grupo grupo) {
		grupo.removeAtendete(voluntario);
		this.grupo.save(grupo);
	}
	
	public void saveAtendimento(Atendimento atendimento, Voluntario solicitante) {
		atendimento.setGrupo(atendimento.getCategoria().getGrupo());
		atendimento.setSolicitante(solicitante);
		this.atendimento.save(atendimento);
	}
	
	public Iterable<Atendimento> findMyAtendimentos(Voluntario solicitante){
		return atendimento.findBySolicitanteAndStatusNot(solicitante, 2);
	}
	
	public void saveFeedback(Feedback feedback) {
		this.feedback.save(feedback);
	}
	
	public Page<Feedback> allFeedbacks(Integer page){
		return feedback.findByStatusOrderByCriacaoDesc(1,PageRequest.of(page, 12));
	}
	
	public Grupo findGrupo(Voluntario voluntario) {
		return this.grupo.findByAtendentes(voluntario);
	}
	
	public Iterable<Atendimento> findAtendimentos(Grupo grupo){
		return this.atendimento.findByGrupoAndStatus(grupo, 0);
	}
	
	public void capturaAtendimento(Atendimento atendimento, Voluntario responsavel) {
		if(atendimento.getResponsavel() == null) {
			atendimento.setResponsavel(responsavel);
			this.atendimento.save(atendimento);
		}
	}
	
	public void descapturaAtendimento(Atendimento atendimento, Voluntario responsavel) {
		if(ehResponsavel(atendimento, responsavel)) {
			atendimento.setResponsavel(null);
			this.atendimento.save(atendimento);
		}
	}
	
	public void respondeAtendimento(Atendimento atendimento, Voluntario responsavel) {
		if(ehResponsavel(atendimento, responsavel)) {
			atendimento.setStatus(1);
			this.atendimento.save(atendimento);
		}
	}
	
	private boolean ehResponsavel(Atendimento atendimento, Voluntario responsavel) {
		return responsavel.getId().equals(atendimento.getResponsavel().getId());
	}
	
	public void concluiAtendimento(Atendimento atendimento) {
		atendimento.setStatus(2);
		this.atendimento.save(atendimento);
	}
	
	public Integer minhasRespostas(Voluntario solicitante) {
		return this.atendimento.countRespostas(solicitante);
	}
	
	public Integer ouvidoriasAbertas(Voluntario voluntario) {
		Grupo grupo = this.grupo.findByAtendentes(voluntario);
		return this.atendimento.countOuvidoriasAbertas(grupo);
	}

}
