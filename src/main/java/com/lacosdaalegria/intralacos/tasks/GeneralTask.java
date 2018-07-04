package com.lacosdaalegria.intralacos.tasks;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;

@Component
@RequiredArgsConstructor
public class GeneralTask {

	private @NonNull AtividadeService service;
	private @NonNull OngsService ongs;
	private @NonNull VoluntarioService voluntarioService;

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
