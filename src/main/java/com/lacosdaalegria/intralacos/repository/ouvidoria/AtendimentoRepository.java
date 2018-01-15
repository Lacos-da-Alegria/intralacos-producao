package com.lacosdaalegria.intralacos.repository.ouvidoria;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.ouvidoria.Atendimento;
import com.lacosdaalegria.intralacos.model.ouvidoria.Grupo;

@Repository
public interface AtendimentoRepository extends CrudRepository<Atendimento, Long>{

	Iterable<Atendimento> findBySolicitanteAndStatusNot(Voluntario solicitante, Integer status);
	Iterable<Atendimento> findByGrupoAndStatus(Grupo grupo, Integer status);
	
	@Query("SELECT count(a) FROM Atendimento a WHERE a.solicitante = :solicitante AND a.status = 1")
	Integer countRespostas(@Param("solicitante") Voluntario solicitante);
	
	@Query("SELECT count(a) FROM Atendimento a WHERE a.responsavel IS NULL AND a.status = 0 and a.grupo = :grupo")
	Integer countOuvidoriasAbertas(@Param("grupo") Grupo grupo);

}
