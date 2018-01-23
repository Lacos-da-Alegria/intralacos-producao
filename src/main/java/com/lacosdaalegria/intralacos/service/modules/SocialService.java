package com.lacosdaalegria.intralacos.service.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.social.Depoimento;
import com.lacosdaalegria.intralacos.repository.social.DepoimentoRepository;

@Service
public class SocialService {
	
	@Autowired
	private DepoimentoRepository depoimento;
	
	
	public Depoimento saveDepoimento(Depoimento depoimento, Voluntario voluntario) {
		depoimento.setVoluntario(voluntario);
		return this.depoimento.save(depoimento);
	}
	
	public Page<Depoimento> pageDepoimento(Integer page){
		return this.depoimento.findByStatusOrderByDtCriacaoDesc(1, PageRequest.of(page, 12));
	}

	public void deleteDepoimento(Depoimento depoimento) {
		depoimento.setStatus(2);
		this.depoimento.save(depoimento);
	}
	
}
