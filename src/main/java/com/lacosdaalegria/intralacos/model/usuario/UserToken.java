package com.lacosdaalegria.intralacos.model.usuario;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import static com.lacosdaalegria.intralacos.model.usuario.enuns.StatusEnum.ATIVO;

@DynamicUpdate
@Table @Entity
@Getter @Setter
public class UserToken {

	@Id @GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;

	@NotNull @ManyToOne
	private Voluntario voluntario;

	@Column(unique = true)
	private String token = UUID.randomUUID().toString();

	@NonNull @ManyToOne
	private Status status = ATIVO.obj();

	private Date criacao = new Date();

    @NonNull @ManyToOne
	private TokenType tokenType;

	public static UserToken build(TokenTypeEnum tokenTypeEnum, Voluntario voluntario){
        UserToken userToken = new UserToken();
        userToken.setVoluntario(voluntario);
        userToken.setTokenType(tokenTypeEnum.obj());
        return userToken;
    }
}
