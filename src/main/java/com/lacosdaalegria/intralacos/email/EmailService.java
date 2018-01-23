package com.lacosdaalegria.intralacos.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.ResetToken;

@Component
public class EmailService {

	@Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendToken(ResetToken token) {
    	String para = token.getVoluntario().getEmail();
    	String assunto = "Recuperando de Senha do Intra Laços";
    	String corpo = "Oie " + token.getVoluntario().getNome() + " , ai esta um link para você realizar "
         		+ "seu reset de senha.\n  Esse link tem validade de 2 horas, certo?.\n" 
         		+ "Estamos esperando você em uma das atividades do Laços da Alegria:\n\n"
         		+ "link: http://intra.lacosdaalegria.com/cadastro/reset/senha?token=" + token.getToken() + "&login=" + token.getVoluntario().getLogin();
    	
    	sendMail(para, assunto, corpo);
    	
    }
    
    private void sendMail(String to, String subject, String body){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
			helper = new MimeMessageHelper(message, true);
	        helper.setFrom(from);
	        helper.setSubject(subject);
	        helper.setTo(to);
	        helper.setText(body, false);//true indicates body is html
        } catch (MessagingException e) {
			e.printStackTrace();
		}
        javaMailSender.send(message);
    }
    
    
    
	
}
