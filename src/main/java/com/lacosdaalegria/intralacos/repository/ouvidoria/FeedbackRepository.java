package com.lacosdaalegria.intralacos.repository.ouvidoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ouvidoria.Feedback;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long>{

	Page<Feedback> findByStatusOrderByCriacaoDesc(Integer status, Pageable page);
	
}
