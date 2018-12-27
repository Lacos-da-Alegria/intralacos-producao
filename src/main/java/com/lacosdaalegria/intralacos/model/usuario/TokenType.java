package com.lacosdaalegria.intralacos.model.usuario;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table @Entity
@Getter @Setter
public class TokenType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

}
