package com.lacosdaalegria.intralacos.repository.ouvidoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ouvidoria.Grupo;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Long>{
	
	Grupo findByAtendentes(Voluntario atendente);
	
}
