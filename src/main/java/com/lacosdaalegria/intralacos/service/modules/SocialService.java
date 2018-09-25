package com.lacosdaalegria.intralacos.service.modules;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lacosdaalegria.intralacos.model.social.Carona;
import com.lacosdaalegria.intralacos.model.social.Depoimento;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.social.CaronaRepository;
import com.lacosdaalegria.intralacos.repository.social.DepoimentoRepository;
import com.lacosdaalegria.intralacos.session.UserInfo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SocialService {
	
	private @NonNull DepoimentoRepository depoimento;
	private @NonNull CaronaRepository carona;
	
	/*
	 * ======================================================================================
	 * ===================================== Depoimentos ====================================
	 * ======================================================================================
	 */
	
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
	
	/*
	 * ======================================================================================
	 * ======================================= Caronas ======================================
	 * ======================================================================================
	 */
	
	public Page<Carona> pagecarona(Integer page){
		return this.carona.findByStatusOrderByCriacaoDesc(1, PageRequest.of(page, 12));
	}
	
	public Carona createCarona(Carona carona, UserInfo info) {
		carona.setCriador(info.getVoluntario());
		return this.carona.save(carona);
	}
	
	public Carona queroCarona(Carona carona, UserInfo info) {
		carona.getViajantes().add(info.getVoluntario());
		return this.carona.save(carona);
	}
	
	public boolean cacelarCarona(Carona carona, UserInfo info) {
		if(carona.ehCriador(info.getVoluntario())) {
			this.carona.delete(carona);
			return true;
		} else 
			return false;
	}
}
