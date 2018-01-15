package com.lacosdaalegria.intralacos.repository.ongs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ongs.Instituicao;
import com.lacosdaalegria.intralacos.model.ongs.Polo;

@Repository
public interface InstituicaoRepository extends CrudRepository<Instituicao, Long>{

	Iterable<Instituicao> findByPolo(Polo polo);
	
}
