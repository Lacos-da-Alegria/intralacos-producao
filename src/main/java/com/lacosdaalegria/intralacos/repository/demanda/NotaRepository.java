package com.lacosdaalegria.intralacos.repository.demanda;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.demanda.Demanda;
import com.lacosdaalegria.intralacos.model.demanda.Nota;

@Repository
public interface NotaRepository extends CrudRepository<Nota, Long>{

	Iterable<Nota> findByDemanda(Demanda demanda);
}
