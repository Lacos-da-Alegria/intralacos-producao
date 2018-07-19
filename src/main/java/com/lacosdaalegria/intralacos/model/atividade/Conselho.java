package com.lacosdaalegria.intralacos.model.atividade;

import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Table
@Getter
@Setter
@Entity
@DynamicUpdate
public class Conselho {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;

    @OneToMany
    private Set<Voluntario> conselheiros;

    @OneToMany
    private Set<Hospital> hospitais;

}
