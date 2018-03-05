package com.lacosdaalegria.intralacos.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class MaisLacos {

	private Integer voluntarios;
	private Integer novatos;
	private Integer hospitais = 0;
	private Integer ongs = 0;
	private Integer horas;

}
