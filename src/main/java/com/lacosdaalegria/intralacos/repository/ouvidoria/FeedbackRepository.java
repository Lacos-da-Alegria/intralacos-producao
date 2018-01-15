package com.lacosdaalegria.intralacos.repository.ouvidoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ouvidoria.Feedback;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long>{

}
