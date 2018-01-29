package com.lacosdaalegria.intralacos.repository.capacitacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.capacitacao.Aula;
import com.lacosdaalegria.intralacos.model.capacitacao.Materia;

@Repository
public interface AulaRepository extends CrudRepository<Aula, Long>{
	
	Iterable<Aula> findByMateria(Materia materia);
	Iterable<Aula> findByMateriaAndStatus(Materia materia, Integer status);

}
