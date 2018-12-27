package com.lacosdaalegria.intralacos.model.usuario;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Table @Entity
@Getter @Setter
public class Status {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String nome;

}
