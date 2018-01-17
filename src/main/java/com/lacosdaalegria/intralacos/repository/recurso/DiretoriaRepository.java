package com.lacosdaalegria.intralacos.repository.recurso;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.recurso.Diretoria;

@Repository
public interface DiretoriaRepository extends CrudRepository<Diretoria, Long> {
	
	@Query("SELECT d.diretores FROM Diretoria d WHERE d = :diretoria order by d.ordem asc")
	Set<Voluntario> findDiretores(@Param("diretoria") Diretoria diretoria);
	
	Diretoria findByDiretores(Voluntario diretor);
}
