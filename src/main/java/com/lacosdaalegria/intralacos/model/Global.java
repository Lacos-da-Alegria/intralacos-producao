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
		return ehMesmaSemanaENaoDomingo(date) || ehSemanaDiferenteEhDomingoSemanaProxima(date);
	}

    public static Date addDays(Integer day) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    public static Date addMonth(Integer month) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

	private static boolean ehMesmaSemanaENaoDomingo(Date date){

		int s1 = getSemanaDoAno(new Date());
		int s2 = getSemanaDoAno(date);

		return s1 == s2 && dia(date) != 1;
	}

	private static boolean ehSemanaDiferenteEhDomingoSemanaProxima(Date date){

		int s1 = getSemanaDoAno(new Date());
		int s2 = getSemanaDoAno(date);

		return s1 != s2 && dia(date) == 1 && ehProximaSemana(s1, s2);
	}

	private static boolean ehProximaSemana(int s1, int s2){
		return Math.abs(s1 -  s2) == 1;
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
