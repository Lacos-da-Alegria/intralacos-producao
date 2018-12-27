package com.lacosdaalegria.intralacos.repository.usuario;

import com.lacosdaalegria.intralacos.model.usuario.ControleEntrada;
import com.lacosdaalegria.intralacos.model.usuario.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ControleEntradaRepository extends CrudRepository<ControleEntrada, Long> {

    @Query("SELECT c FROM ControleEntrada c WHERE c.status = :status")
    Iterable<ControleEntrada> findByTokenTypeAndStatus(@Param("status") Status status);

    @Query("SELECT c FROM ControleEntrada c WHERE c.status = 1 AND c.userToken.token = :token")
    ControleEntrada findByToken(@Param("token") String token);
}
