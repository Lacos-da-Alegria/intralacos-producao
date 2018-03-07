package com.lacosdaalegria.intralacos.repository.ongs;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ongs.Agenda;
import com.lacosdaalegria.intralacos.model.ongs.Polo;

@Repository
public interface AgendaRepository extends CrudRepository<Agenda, Long>{

	@Query("SELECT v FROM Agenda v WHERE v.instituicao.polo = :polo and v.status = 1 and v.criacao >= :data")
	Iterable<Agenda> findByPolo(@Param("polo") Polo polo, @Param("data") Date inicio);
	
	Iterable<Agenda> findByHorarioBetweenAndStatus(Date inicio, Date fim, Integer status);
	
	Iterable<Agenda> findByInscricaoTrueAndStatus(Integer status);

	@Query("SELECT v FROM Agenda v WHERE v.instituicao.polo = :polo and v.status = 1 and v.chamada = true")
	Iterable<Agenda> chamadaPolo(@Param("polo") Polo polo);
}
