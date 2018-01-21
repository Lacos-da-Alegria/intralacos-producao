package com.lacosdaalegria.intralacos.repository.atividade;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.Voluntario;
import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.atividade.Registro;
import com.lacosdaalegria.intralacos.model.atividade.Semana;
import com.lacosdaalegria.intralacos.model.ongs.Agenda;

@Repository
public interface RegistroRepository extends CrudRepository<Registro, Long>{
	
	Iterable<Registro> findByHospitalAndStatusAndSemana(Hospital hospital, Integer status, Semana semana);
	Iterable<Registro> findByAgendaAndStatusAndSemana(Agenda agenda, Integer status, Semana semana);
	Iterable<Registro> findByVoluntarioAndStatusAndSemana(Voluntario voluntario, Integer status, Semana semana);
	
	@Query("SELECT r FROM Registro r WHERE r.status != 2 and r.hospital = :h and r.semana = :s order by r.posicao asc, r.criacao asc")
	List<Registro> findFilaHospital(@Param("h") Hospital hospital, @Param("s") Semana semana);
	@Query("SELECT r FROM Registro r WHERE r.status != 2  and r.agenda = :a and r.semana = :s order by r.posicao asc, r.criacao asc")
	List<Registro> findFilaAcao(@Param("a") Agenda agenda, @Param("s") Semana semana);
	
	@Query("SELECT count(r) FROM Registro r WHERE r.status = 0 and r.hospital = :h and r.semana = :s and r.tipo = 1")
	Integer countNovatosIncritos(@Param("h") Hospital hospital, @Param("s") Semana semana);
	
	@Query("SELECT r.voluntario FROM Registro r WHERE r.status = 0 and r.hospital = :h and r.semana = :s and r.tipo = 1")
	Iterable<Voluntario> findNovatosIncritos(@Param("h") Hospital hospital, @Param("s") Semana semana);
	
	@Query("SELECT count(r) FROM Registro r WHERE r.status = 1 and r.semana.ano = :ano")
	Integer visitaEsseAno(@Param("ano") Integer ano);
	
	@Query("SELECT count(r) FROM Registro r WHERE r.status = 1 and r.voluntario = :voluntario and r.hospital IS NOT NULL")
	Integer visitaHospitais(@Param("voluntario") Voluntario voluntario);
	
	@Query("SELECT count(r) FROM Registro r WHERE r.status = 1 and r.voluntario = :voluntario and r.agenda IS NOT NULL")
	Integer visitaAcoes(@Param("voluntario") Voluntario voluntario);
	
	Iterable<Registro> findByVoluntarioAndStatusAndCriacaoAfter(Voluntario voluntario, Integer status, Date date);
}
