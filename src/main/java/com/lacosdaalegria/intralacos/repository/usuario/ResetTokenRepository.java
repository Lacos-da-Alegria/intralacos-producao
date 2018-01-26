package com.lacosdaalegria.intralacos.repository.usuario;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.usuario.ResetToken;

@Repository
public interface ResetTokenRepository extends CrudRepository<ResetToken, Long>{
	
	ResetToken findByTokenAndStatusAndCriacaoGreaterThanEqual(String token, Integer status, Date date);
}
