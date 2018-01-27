package com.lacosdaalegria.intralacos.repository.recurso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.recurso.Diretoria;
import com.lacosdaalegria.intralacos.model.recurso.Equipe;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface EquipeRepository extends CrudRepository<Equipe, Long>{

	Equipe findByMembrosOrLider(Voluntario membro, Voluntario lider);
	
	Equipe findByLider(Voluntario lider);
	
	Iterable<Equipe> findByDiretoria(Diretoria diretoria);
}
