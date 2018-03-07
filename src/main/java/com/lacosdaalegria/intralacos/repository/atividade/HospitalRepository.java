package com.lacosdaalegria.intralacos.repository.atividade;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.atividade.Hospital;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital, Long>{

	Iterable<Hospital> findByStatus(Integer status);
	
	Iterable<Hospital> findByStatusAndChamadaFalse(Integer status);
	
	Iterable<Hospital> findByNovatosTrue();
	
	Iterable<Hospital> findByDiaAndPeriodo(Integer dia, Integer periodo);
	
}
