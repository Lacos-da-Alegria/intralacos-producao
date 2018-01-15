package com.lacosdaalegria.intralacos.repository.ouvidoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ouvidoria.Grupo;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Long>{
	
	Grupo findByAtendentes(Voluntario atendente);
	
}
