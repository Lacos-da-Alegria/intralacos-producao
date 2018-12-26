package com.lacosdaalegria.intralacos.model.usuario;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static com.lacosdaalegria.intralacos.model.usuario.enuns.StatusEnum.ATIVO;
import static com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum.ATIVAR_NOVATO;

@Entity @Table
@Getter @Setter
@NoArgsConstructor
public class ControleEntrada {

    @Id @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserToken userToken;

    @ManyToOne
    private Status status;

    private Date criacao = new Date();

    public static ControleEntrada build(Voluntario voluntario){
        ControleEntrada controleEntrada = new ControleEntrada();
        controleEntrada.setUserToken(UserToken.build(ATIVAR_NOVATO, voluntario));
        controleEntrada.setStatus(ATIVO.obj());
        return controleEntrada;
    }
}
