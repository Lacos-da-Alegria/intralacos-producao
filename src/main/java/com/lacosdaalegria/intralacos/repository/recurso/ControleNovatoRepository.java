package com.lacosdaalegria.intralacos.repository.recurso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.recurso.ControleNovato;

@Repository
public interface ControleNovatoRepository extends CrudRepository<ControleNovato, Long>{
	ControleNovato findByVoluntario(Voluntario voluntario);
}
