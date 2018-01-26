package com.lacosdaalegria.intralacos.repository.sustentacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.sustentacao.Analista;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface AnalistaRepository extends CrudRepository<Analista, Long>{

	Analista findByVoluntario(Voluntario voluntario);
	
}
