package com.lacosdaalegria.intralacos.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Regiao;
import com.lacosdaalegria.intralacos.model.ongs.Polo;

@Repository
public interface RegiaoRepository extends CrudRepository<Regiao, Long> {

	Iterable<Regiao> findByStatusOrderByNome(Integer status);
	
	Iterable<Regiao> findByPoloIsNull();
	
	Set<Regiao> findByPolo(Polo polo);
	
}
