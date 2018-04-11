package com.lacosdaalegria.intralacos.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.service.modules.AtividadeService;

@Component
public class AtividadeTask {
	
	@Autowired
	private AtividadeService service;

	// Fecha a lista as 20hs do dia anterior
	@Scheduled(cron="0 0 20 * * *")
	public void atividadesHospitaisMatutinas() {
		service.atividadesHospitaisMatutinas();
	}

	@Scheduled(cron="0 0 7 * * *")
	public void atividadesOngsMatutinas() {
		service.atividadesOngsMatutinas();
	}
	
	@Scheduled(cron="0 0 10 * * *")
	public void atividadesHospitaisVespertinas() {
		service.atividadesHospitaisVespertinas();
	}

	@Scheduled(cron="0 0 12 * * *")
	public void atividadesOngsVespertinas() {
		service.atividadesOngsVespertinas();
	}
	
	@Scheduled(cron="0 0 18 * * *")
	public void atividadesHospitaisNoturnas() {
		service.atividadesHospitaisNoturnas();
	}

	@Scheduled(cron="0 0 18 * * *")
	public void atividadesOngsNoturnas() {
		service.atividadesOngsNoturnas();
	}

}
