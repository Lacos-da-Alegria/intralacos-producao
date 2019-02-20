package com.lacosdaalegria.intralacos.tasks;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.service.modules.AtividadeService;
import com.lacosdaalegria.intralacos.service.modules.OngsService;
import com.lacosdaalegria.intralacos.service.modules.VoluntarioService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Log
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
		voluntarioService.desativarVoluntarios();
	}

	@Scheduled(cron="0 * * ? * SUN")
	public void novatosAtivos() {

	    log.info("Início do controle de registros de novatos ativos e inativos");

	    voluntarioService.gerarTokenConfirmacaoNovatos();

	    log.info("Fim da rotina de controle de registros de novatos ativos e inativos");

	}

    @Scheduled(cron="* * 10 * * MON,WED,FRI")
    public void enviarEmailNovatos() {

        log.info("Início da rotina envio de email para os novatos que estão proximos para visitar");

        voluntarioService.enviarEmailConfirmacao();

        log.info("Fim da rotina envio de email para os novatos que estão proximos para visitar");

    }

}
