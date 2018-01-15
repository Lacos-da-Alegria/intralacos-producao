package com.lacosdaalegria.intralacos.repository.demanda;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.demanda.Demanda;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;

@Repository
public interface DemandaRepository extends CrudRepository<Demanda, Long>{

	Iterable<Demanda> findByEquipeAndStatusNot(Equipe equipe, Integer Status);
	Iterable<Demanda> findByEquipeAndStatus(Equipe equipe, Integer Status);
	
}
