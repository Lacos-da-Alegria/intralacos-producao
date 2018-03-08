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
	
	public static boolean ehEssaSemana(Date date) {
		int s1 = getSemanaDoAno(new Date());
		int s2 = getSemanaDoAno(date);
		System.out.println(s1 + " - " + s2);
		if(dia(date) == 1)
			return s1 == (s2-1);
		else
			return s1 == s2;
	}
	
	private static int getSemanaDoAno(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	private static int dia(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	private static int dia(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	
}
