package com.lacosdaalegria.intralacos.model.usuario.enuns;

import com.lacosdaalegria.intralacos.model.usuario.TokenType;
import lombok.Getter;

@Getter
public enum TokenTypeEnum {

    RESETAR_SENHA(1l, "Resetar Senha"),
    CONFIRMAR_EMAIL(2l, "Confirmar Email"),
    ATIVAR_NOVATO(3l, "Ativar Novato");

    private Long id;
    private String type;

    TokenTypeEnum(Long id, String type){
        this.id = id;
        this.type = type;
    }

    public TokenType obj(){
        TokenType tokenType = new TokenType();
        tokenType.setId(id);
        tokenType.setType(type);
        return tokenType;
    }

    public boolean equals(TokenType tokenType){
        return id.equals(tokenType.getId());
    }

}
