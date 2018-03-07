package com.lacosdaalegria.intralacos.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;

@Component
public class GeneralTask {
	
	@Autowired
	private AtividadeService service;
	@Autowired
	private OngsService ongs;
	
	@Scheduled(cron="0 0 0 ? * MON")
	public void initNovaSemana() {
		System.out.println("Cria nova Semana!");
		Semana semana = service.novaSemana();
		ongs.ativaAcoesDessaSemana();
		service.liberarInscricao(semana);
	}
	
}
