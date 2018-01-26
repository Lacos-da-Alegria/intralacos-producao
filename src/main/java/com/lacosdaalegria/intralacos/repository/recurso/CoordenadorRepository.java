package com.lacosdaalegria.intralacos.repository.recurso;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.recurso.Coordenador;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface CoordenadorRepository extends CrudRepository<Coordenador, Long>{

	Iterable<Coordenador> findByStatus(Integer status);
	Coordenador findByVoluntario(Voluntario voluntario);
}
