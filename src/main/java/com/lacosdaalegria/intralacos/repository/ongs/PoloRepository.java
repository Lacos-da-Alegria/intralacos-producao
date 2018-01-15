package com.lacosdaalegria.intralacos.repository.ongs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Regiao;
import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ongs.Polo;

@Repository
public interface PoloRepository extends CrudRepository<Polo, Long>{

	Polo findByMembros(Voluntario membro);
	Polo findByRegioes(Regiao regiao);
	
}
