package com.lacosdaalegria.intralacos.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

//Mudar nome para Calendario
//Utilizar para regras de data gerais
@Component
public class Global {

	public static boolean rodadaRandomica(){
		return dia() == 2 || dia() == 3;
	}
	
	private static int dia(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	
}
