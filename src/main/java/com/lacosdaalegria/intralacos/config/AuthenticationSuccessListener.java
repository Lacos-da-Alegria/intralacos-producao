package com.lacosdaalegria.intralacos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.session.UserInfo;

/**
 * Listener responsável por iniciar objeto de sessão no login do usuário. 
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
	private UserInfo info;
	
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
    	info.initSession();
    }

}
