package com.lacosdaalegria.intralacos.repository.capacitacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.capacitacao.Educador;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface EducadorRepository extends CrudRepository<Educador, Long>{
	
	Educador findByVoluntario(Voluntario voluntario);

}
