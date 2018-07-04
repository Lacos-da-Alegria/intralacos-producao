package com.lacosdaalegria.intralacos.model.usuario;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ACEITE(1l, "ROLE_ACEITE"),
    NOVATO(2l, "ROLE_NOVATO"),
    VOLUNTARIO(3l, "ROLE_VOLUNTARIO"),
    ADMIN(4l, "ROLE_ADMIN"),
    ATENDIMENTO(5l, "ROLE_ATEND"),
    EXECUTIVO(6l, "ROLE_DEXEC"),
    HOSPITAL(7l, "ROLE_DHOSP"),
    ONGS(8l, "ROLE_DONGS"),
    COMUNICACAO(9l, "ROLE_DCOM"),
    POLO(10l, "ROLE_ONGS"),
    DIRETOR(11l, "ROLE_DIRETOR"),
    LIDER(12l, "ROLE_LIDER"),
    DEMANDA(13l, "ROLE_DEMANDA"),
    COORDENADOR(14l, "ROLE_COORD"),
    CONTROLE_NOVATOS(15l, "ROLE_CONTROL"),
    APOIO(16l, "ROLE_APOIO"),
    SUSTENTACAO(17l, "ROLE_SUSTENTA"),
    CAPACITACAO(6294l, "ROLE_CAPACITA"),
    NOVATO_ONGS(16833l, "ROLE_NOVATO_ONGS"),
    DESCONHECIDO(0l, "Desconhecido");

    private Long codigo;
    private String papel;

    RoleEnum(Long codigo, String papel){
        this.codigo = codigo;
        this.papel = papel;
    }

    public static RoleEnum getById(Long id) {
        for(RoleEnum e : values()) {
            if(e.codigo.equals(id)) return e;
        }
        return DESCONHECIDO;
    }

    public static RoleEnum getByPapel(String papel) {
        for(RoleEnum e : values()) {
            if(e.papel.equals(papel)) return e;
        }
        return DESCONHECIDO;
    }
}
