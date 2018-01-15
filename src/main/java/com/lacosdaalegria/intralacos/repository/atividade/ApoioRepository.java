package com.lacosdaalegria.intralacos.repository.atividade;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.atividade.Apoio;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;

@Repository
public interface ApoioRepository extends CrudRepository<Apoio, Long>{

	Iterable<Apoio> findByHospital(Hospital hospital);
	Apoio findByVoluntario(Voluntario voluntario);
}
