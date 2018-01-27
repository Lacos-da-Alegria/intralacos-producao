package com.lacosdaalegria.intralacos.repository.ongs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ongs.Polo;
import com.lacosdaalegria.intralacos.model.usuario.Regiao;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;

@Repository
public interface PoloRepository extends CrudRepository<Polo, Long>{

	Polo findByMembros(Voluntario membro);
	Polo findByRegioes(Regiao regiao);
	
}
