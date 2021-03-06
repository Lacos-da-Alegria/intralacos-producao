package com.lacosdaalegria.intralacos.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lacosdaalegria.intralacos.model.ongs.Polo;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.repository.usuario.RegiaoRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegiaoService {

	private @NonNull RegiaoRepository repository;
	
	public Iterable<Regiao> getAllActive(){
		return repository.findByStatusOrderByNome(1);
	}
	
	public Iterable<Regiao> semPolo(){
		return repository.findByPoloIsNull();
	}
	
	public void addPolo(Regiao regiao, Polo polo) {
		regiao.setPolo(polo);
		repository.save(regiao);
	}
	
	public void addPolo(Iterable<Regiao> regiao, Polo polo) {
		for(Regiao r : regiao) {
			r.setPolo(polo);
		}
		repository.saveAll(regiao);
	}
	
	public void removePolo(Regiao regiao) {
		regiao.setPolo(null);
		repository.save(regiao);
	}
	
	public Set<Regiao> poloRegioes(Polo polo){
		return repository.findByPolo(polo);
	}
}
