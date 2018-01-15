package com.lacosdaalegria.intralacos.repository.sustentacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.sustentacao.Analista;

@Repository
public interface AnalistaRepository extends CrudRepository<Analista, Long>{

	Analista findByVoluntario(Voluntario voluntario);
	
}
