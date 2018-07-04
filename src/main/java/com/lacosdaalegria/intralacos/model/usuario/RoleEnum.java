package com.lacosdaalegria.intralacos.model.usuario;

public enum RoleEnum {

    ACEITE(1, "ROLE_ACEITE"),
    NOVATO(2, "ROLE_NOVATO"),
    VOLUNTARIO(3, "ROLE_VOLUNTARIO"),
    ADMIN(4, "ROLE_ADMIN"),
    ATENDIMENTO(5, "ROLE_ATEND"),
    EXECUTIVO(6, "ROLE_DEXEC"),
    HOSPITAL(7, "ROLE_DHOSP"),
    ONGS(8, "ROLE_DONGS"),
    COMUNICACAO(9, "ROLE_DCOM"),
    POLO(10, "ROLE_ONGS"),
    DIRETOR(11, "ROLE_DIRETOR"),
    LIDER(12, "ROLE_LIDER"),
    DEMANDA(13, "ROLE_DEMANDA"),
    COORDENADOR(14, "ROLE_COORD"),
    CONTROLE_NOVATOS(15, "ROLE_CONTROL"),
    APOIO(16, "ROLE_APOIO"),
    SUSTENTACAO(17, "ROLE_SUSTENTA"),
    CAPACITACAO(6294, "ROLE_CAPACITA"),
    NOVATO_ONGS(16833, "ROLE_NOVATO_ONGS");

    private int codigo;
    private String papel;

    RoleEnum(int codigo, String papel){
        this.codigo = codigo;
        this.papel = papel;
    }
}
