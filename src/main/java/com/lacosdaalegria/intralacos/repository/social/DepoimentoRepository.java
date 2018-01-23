package com.lacosdaalegria.intralacos.repository.social;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.social.Depoimento;

@Repository
public interface DepoimentoRepository extends CrudRepository<Depoimento, Long>{
	
	Page<Depoimento> findByStatusOrderByDtCriacaoDesc(Integer status, Pageable page);

}
