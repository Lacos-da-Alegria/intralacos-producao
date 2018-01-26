package com.lacosdaalegria.intralacos.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.ongs.Polo;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.repository.usuario.RegiaoRepository;

@Service
public class RegiaoService {

	@Autowired
	RegiaoRepository repository;
	
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
