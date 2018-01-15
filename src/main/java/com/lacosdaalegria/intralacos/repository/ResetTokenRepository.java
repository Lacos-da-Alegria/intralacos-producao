package com.lacosdaalegria.intralacos.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.ResetToken;

@Repository
public interface ResetTokenRepository extends CrudRepository<ResetToken, Long>{
	
	ResetToken findByTokenAndStatusAndCriacaoGreaterThanEqual(String token, Integer status, Date date);
}
