package com.lacosdaalegria.intralacos.model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

//Mudar nome para Calendario
//Utilizar para regras de data gerais
@Component
public class Global {

	private static String CODIGO = "lacos"+UUID.randomUUID().toString().substring(0, 4);
	
	public static String getCodigo() {
		return Global.CODIGO;
	}
	
	public static void newCodigo() {
		Global.CODIGO = "lacos"+UUID.randomUUID().toString().substring(0, 4);
	}
	
	public static boolean rodadaRandomica(){
		return dia() == 2 || dia() == 3;
	}
	
	private static int dia(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	
}
