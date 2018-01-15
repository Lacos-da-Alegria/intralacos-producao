package com.lacosdaalegria.intralacos.repository.ouvidoria;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ouvidoria.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
	
	Iterable<Categoria> findByStatus(Integer status);

}
