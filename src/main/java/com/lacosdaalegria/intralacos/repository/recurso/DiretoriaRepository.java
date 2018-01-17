package com.lacosdaalegria.intralacos.repository.recurso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.recurso.Diretoria;

@Repository
public interface DiretoriaRepository extends CrudRepository<Diretoria, Long> {
	
	Diretoria findByDiretores(Voluntario diretor);
}
