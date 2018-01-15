package com.lacosdaalegria.intralacos.repository.atividade;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.atividade.Semana;

@Repository
public interface SemanaRepository extends CrudRepository<Semana, Long>{

	Semana findFirstByOrderByIdDesc();
	
}
