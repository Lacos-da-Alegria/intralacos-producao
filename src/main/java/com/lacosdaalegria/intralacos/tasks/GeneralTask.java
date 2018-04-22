package com.lacosdaalegria.intralacos.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;

@Component
public class GeneralTask {

	private AtividadeService service;
	private OngsService ongs;
	private VoluntarioService voluntarioService;
	
	@Autowired
	public GeneralTask(AtividadeService service, OngsService ongs, VoluntarioService voluntarioService) {
		this.service = service;
		this.ongs = ongs;
		this.voluntarioService = voluntarioService;
	}
	
	
	@Scheduled(cron="0 0 0 ? * MON")
	public void initNovaSemana() {
		Semana semana = service.novaSemana();
		ongs.ativaAcoesDessaSemana();
		service.liberarInscricao(semana);
	}
	
	@Scheduled(cron="0 * * ? * SUN")
	public void desativaAtivaVoluntarios() {
		voluntarioService.ativarVoluntarios();
		voluntarioService.desativarVoluntarios();
	}
	
}
