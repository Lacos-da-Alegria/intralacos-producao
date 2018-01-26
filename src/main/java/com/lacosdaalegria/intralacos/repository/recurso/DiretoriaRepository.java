package com.lacosdaalegria.intralacos.repository.recurso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.recurso.Diretoria;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface DiretoriaRepository extends CrudRepository<Diretoria, Long> {
	
	Diretoria findByDiretores(Voluntario diretor);
}
