package com.lacosdaalegria.intralacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@EnableScheduling
@SpringBootApplication
public class IntralacosApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntralacosApplication.class, args);
	}
	
	/*//Bloco para inicilização de dados para banco local de desenvolvimento
	@EventListener(ApplicationReadyEvent.class)
	public void startup() {
		data.start();
	}*/

	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.US);
	    return slr;
	}
	
}
