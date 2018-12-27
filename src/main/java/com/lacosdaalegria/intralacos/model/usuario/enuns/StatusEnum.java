package com.lacosdaalegria.intralacos.model.usuario.enuns;

import com.lacosdaalegria.intralacos.model.usuario.Status;
import lombok.Getter;

@Getter
public enum StatusEnum {

    ATIVO(1l, "Ativo"),
    INATIVO(2l, "Inativo"),
    CONFIRMADO(3l, "Confirmada Participação"),
    PRESENCA(4L, "Presente na Atividade"),
    FALTA(5l, "Falta na Atividade"),
    SEM_INTERESSE(6l, "Sem Interesse na Atividade");

    private Long id;
    private String nome;

    StatusEnum(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public Status obj(){
        Status status = new Status();
        status.setId(id);
        status.setNome(nome);
        return status;
    }

    public boolean equals(Status status){
        return id.equals(status.getId());
    }

}
