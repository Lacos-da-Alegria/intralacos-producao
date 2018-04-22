package com.lacosdaalegria.intralacos.service.modules;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.ongs.Instituicao;
import com.lacosdaalegria.intralacos.model.ongs.Polo;
import com.lacosdaalegria.intralacos.model.ongs.Tag;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.ongs.AgendaRepository;
import com.lacosdaalegria.intralacos.repository.ongs.InstituicaoRepository;
import com.lacosdaalegria.intralacos.repository.ongs.PoloRepository;
import com.lacosdaalegria.intralacos.repository.ongs.TagRepository;
import com.lacosdaalegria.intralacos.repository.s3.S3;
import com.lacosdaalegria.intralacos.service.RegiaoService;

@Service
public class OngsService {

	@Autowired
	private AgendaRepository agenda;
	@Autowired
	private InstituicaoRepository instituicao;
	@Autowired
	private PoloRepository polo;
	@Autowired
	private TagRepository tag;
	@Autowired
	private RegiaoService regiao;
	@Autowired
	private S3 s3;
	
	public Iterable<Instituicao> findInstituicoes(Polo polo){
		return instituicao.findByPolo(polo);
	}
	
	public Iterable<Polo> allPolos(){
		return polo.findAll();
	}
	
	public Polo myPolo(Voluntario membro) {
		return polo.findByMembros(membro); 
	}
	
	public Iterable<Tag> allTags(){
		return tag.findAll();
	}
	
	public void addPolo(Polo polo) {
		this.polo.save(polo);
		regiao.addPolo(polo.getRegioes(), polo);
	}
	
	public void addMembro(Polo polo, Voluntario membro) {
		polo.getMembros().add(membro);
		this.polo.save(polo);
	}
	
	public void removeMembro(Voluntario membro) {
		Polo polo = this.polo.findByMembros(membro);
		polo.removeMembro(membro);
		this.polo.save(polo);		
	}
	
	public void saveInstituicao(Instituicao instituicao) {
		this.instituicao.save(instituicao);
	}
	
	public void removeRegiao(Regiao regiao) {
		Polo polo =  regiao.getPolo();
		polo.removeRegiao(regiao);
		this.polo.save(polo);
		this.regiao.removePolo(regiao);
	}
	
	public void addRegioes(Polo polo) {
		regiao.addPolo(polo.getRegioes(), polo);
		polo.getRegioes().addAll(regiao.poloRegioes(polo));
		this.polo.save(polo);
	}
	
	public void addTag(Tag tag) {
		this.tag.save(tag);
	}
	
	public void updateTag(Tag tag) {
		tag.inverteStatus();
		this.tag.save(tag);
	}
	
	public Iterable<Tag> activeTags(){
		return tag.findByStatus(1);
	}
	
	public void cancelarAcao(Agenda agenda) {
		if(agenda.isInscricao()) {
			agenda.setStatus(2);
			this.agenda.save(agenda);
		}
	}
	
	public void agendaAcao(Agenda agenda, Voluntario criador) {
		agenda.setCriador(criador);
		verificaSemana(agenda);
		this.agenda.save(agenda);		
	}
	
	private void verificaSemana(Agenda agenda) {
		if(Global.ehEssaSemana(agenda.getHorario())) {
			agenda.setInscricao(true);
		}
	}
	
	public Iterable<Agenda> getAgenda(Polo polo){
		return agenda.findByPolo(polo, addMonth(-1));
	}
	
	public Iterable<Agenda> getChamadas(Polo polo){
		return agenda.chamadaPolo(polo);
	} 
	
	public Iterable<Agenda> agendaPolo(Polo polo){
		return agenda.findByPolo(polo);
	} 
	
	public Iterable<Agenda> calendarioAcoes(){
		return agenda.findByHorarioBetweenAndStatus(addMonth(-1), addMonth(3), 1);
	}
	
	public Iterable<Agenda> getAcoesAtivas(){
		return agenda.findByInscricaoTrueAndStatusOrChamadaTrue(1);
	}
	
	public Iterable<Agenda> getAcoesAtivasNovatos(){
		return agenda.findByInscricaoTrueAndStatusAndNovatosNotNull(1);
	}
	
	public void ativaAcoesDessaSemana() {
		
		Iterable<Agenda> agendas = getAcoesEssaSemana();
		
		for(Agenda agenda : agendas) {
			agenda.setInscricao(true);
		}
		saveAllAgendas(agendas);
	}
	
	private Iterable<Agenda> getAcoesEssaSemana(){
		return agenda.findByHorarioBetweenAndStatus(new Date(), addDays(7), 1);
	}
	
	public void saveAllAgendas(Iterable<Agenda> agendas) {
		agenda.saveAll(agendas);
	}
	
	public void saveAgenda(Agenda agenda) {
		this.agenda.save(agenda);
	}
	
	public void updateImage(MultipartFile file, Instituicao instituicao) {
		String profile = s3.carregaImagem("pic", instituicao.getId().toString(), file);
		instituicao.setImagem("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-318693850464/" + profile);
		this.instituicao.save(instituicao);
	}
	
	private Date addDays(Integer day) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		c.add(Calendar.DATE, day);
		return c.getTime();
	}
	
	private Date addMonth(Integer month) {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		c.add(Calendar.MONTH, month);
		return c.getTime();
	}
	
}
