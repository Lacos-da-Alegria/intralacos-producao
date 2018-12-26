package com.lacosdaalegria.intralacos.repository.usuario;

import java.util.Date;

import com.lacosdaalegria.intralacos.model.usuario.Status;
import com.lacosdaalegria.intralacos.model.usuario.TokenType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.usuario.UserToken;

@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Long>{
	
	UserToken findByTokenAndStatusAndCriacaoGreaterThanEqualAndTokenType(String token, Status status, Date date, TokenType tokenType);

	UserToken findByTokenAndStatusAndTokenType(String token, Status status, TokenType tokenType);
}
