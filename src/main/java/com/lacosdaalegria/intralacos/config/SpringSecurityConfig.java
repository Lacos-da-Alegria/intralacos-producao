package com.lacosdaalegria.intralacos.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	 	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.
		jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder); 
	}
	 	 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 		
		http.csrf().disable()
	        .authorizeRequests()
	        	.antMatchers("/cadastro/**").permitAll()
	        	.antMatchers("/termo/**").hasAnyRole("ACEITE")
	        	.antMatchers("/novato/**").hasAnyRole("NOVATO", "NOVATO_ONGS")
	        	.antMatchers("/voluntario/**").hasAnyRole("VOLUNTARIO")
	        	.antMatchers("/atendimento/**").hasAnyRole("ATEND")
	        	.antMatchers("/demanda/**").hasAnyRole("DEMANDA", "LIDER", "DIRETOR")
	        	.antMatchers("/lider/**").hasAnyRole("LIDER", "DIRETOR")
	        	.antMatchers("/diretor/**").hasAnyRole("DIRETOR")
	        	.antMatchers("/coordenador/**").hasAnyRole("COORD", "DHOSP", "DEXEC")
	        	.antMatchers("/hospitais/**").hasAnyRole( "DHOSP", "DEXEC")
	        	.antMatchers("/executivo/**").hasAnyRole("DEXEC")
	        	.antMatchers("/capacitacao/**").hasAnyRole("CAPACITA", "DEXEC")
	        	.antMatchers("/controlador/**").hasAnyRole("CONTROL", "DHOSP")
	        	.antMatchers("/polo/**").hasAnyRole("ONGS", "DONGS", "DEXEC")
	        	.antMatchers("/ongs/**").hasAnyRole("DONGS", "DEXEC")
	        	.antMatchers("/comunicacao/**").hasAnyRole( "DCOM", "DEXEC")
	        	.antMatchers("/sustentacao/**").hasAnyRole( "SUSTENTA", "ADMIN")
				.anyRequest().authenticated()
	        .and()
	        .formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
	        .logout()
				.permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/assets/**");
    }	

}
