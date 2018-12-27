package com.lacosdaalegria.intralacos.email;


import com.lacosdaalegria.intralacos.model.usuario.TokenType;
import com.lacosdaalegria.intralacos.model.usuario.UserToken;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum.ATIVAR_NOVATO;
import static com.lacosdaalegria.intralacos.model.usuario.enuns.TokenTypeEnum.RESETAR_SENHA;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private static final String HOST = "http://localhost:8888/";

    private String para;
    private String assunto;
    private String template;
    private Map<String, Object> modelo = new HashMap<>();

    private UserToken userToken;

    public Email(UserToken userToken){

        Voluntario voluntario = userToken.getVoluntario();

        this.userToken = userToken;

        para = voluntario.getEmail();

        modelo.put("nome", voluntario.getNome());

        decisionByType(userToken.getTokenType());

    }

    private void decisionByType(TokenType tokenType){
        if(RESETAR_SENHA.equals(tokenType)){
            resetPassword();
        } else {
            if(ATIVAR_NOVATO.equals(tokenType)){
                confirmaParticipacao();
            }
        }
    }

    private void resetPassword(){

        assunto = "Cadastro de Nova Senha para o Intralaços";
        template = "emails/reset-password";

        modelo.put("url", HOST + "cadastro/reset/senha?token=" + userToken.getToken() + "&login=" + userToken.getVoluntario().getLogin());

    }

    private void confirmaParticipacao(){

        assunto = "Confirmar Participação no Laços da Alegria";

        template = "emails/confirma-atividade";

        modelo.put("url", HOST + "cadastro/confirmar/participacao?token=" + userToken.getToken());
        modelo.put("desativar", HOST + "cadastro/desativar?token=" + userToken.getToken());
        modelo.put("imagem", HOST + "assets/img/email-confirma.gif");

    }

    public static Email build(UserToken userToken){
        return new Email(userToken);
    }

}
