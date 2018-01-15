package com.lacosdaalegria.intralacos;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lacosdaalegria.intralacos.dev.InitData;

@EnableScheduling
@SpringBootApplication
public class IntralacosApplication {
	
	@Autowired
	InitData data;

	public static void main(String[] args) {
		SpringApplication.run(IntralacosApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void startup() {
		data.start();
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.US);
	    return slr;
	}
	
}
