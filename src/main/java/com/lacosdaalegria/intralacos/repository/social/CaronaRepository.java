package com.lacosdaalegria.intralacos.repository.social;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.social.Carona;

@Repository
public interface CaronaRepository extends CrudRepository<Carona, Long>{

	Page<Carona> findByStatusOrderByCriacaoDesc(Integer status, Pageable page);
	
}
