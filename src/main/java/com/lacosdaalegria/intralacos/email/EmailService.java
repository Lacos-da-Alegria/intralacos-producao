package com.lacosdaalegria.intralacos.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.lacosdaalegria.intralacos.model.usuario.UserToken;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log
@Component
@RequiredArgsConstructor
public class EmailService {

	@Value("${spring.mail.username}")
    private String from;

    private @NonNull JavaMailSender javaMailSender;
    private @NonNull SpringTemplateEngine templateEngine;

    public void send(Email mail) {

        log.info("Enviando email para: " + mail.getPara());

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper;

        try {

            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getModelo());

            String html = templateEngine.process(mail.getTemplate(), context);

            helper.setTo(mail.getPara());
            helper.setText(html, true);
            helper.setSubject(mail.getAssunto());
            helper.setFrom(from);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);

        log.info("Email enviado!");
    }

}
