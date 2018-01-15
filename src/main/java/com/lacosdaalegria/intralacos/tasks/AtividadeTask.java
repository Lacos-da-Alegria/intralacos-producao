package com.lacosdaalegria.intralacos.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.service.modules.AtividadeService;

@Component
public class AtividadeTask {
	
	@Autowired
	private AtividadeService service;
	
	@Scheduled(cron="0 0 7 * * *")
	public void atividadeMatutina() {
		service.atividadesMatutinas();
	}
	
	@Scheduled(cron="0 0 12 * * *")
	public void atividadeVespertina() {
		service.atividadesVespertinas();
	}
	
	@Scheduled(cron="0 0 18 * * *")
	public void atividadeNoturna() {
		service.atividadesNoturnas();
	}

}
