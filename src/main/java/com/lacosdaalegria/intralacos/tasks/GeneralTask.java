package com.lacosdaalegria.intralacos.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.Global;
import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;

@Component
public class GeneralTask {
	
	@Autowired
	private AtividadeService service;
	
	@Scheduled(cron="0 0 0 ? * MON")
	public void initNovaSemana() {
		System.out.println("Cria nova Semana!");
		Semana semana = service.novaSemana();
		service.liberarInscricao(semana);
	}
	
	@Scheduled(cron="0 0 0 ? * FRI")
	public void newCodigo() {
		System.out.println("Gerando novo codigo!");
		Global.newCodigo();
	}
	
}
