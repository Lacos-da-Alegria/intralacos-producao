package com.lacosdaalegria.intralacos.repository.ongs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ongs.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>{
	
	Iterable<Tag> findByStatus(Integer status);

}
