package com.lacosdaalegria.intralacos.repository.capacitacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.capacitacao.Materia;

@Repository
public interface MateriaRepository extends CrudRepository<Materia, Long>{

	Iterable<Materia> findByStatus(Integer status);
}
