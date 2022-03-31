package ru.liga.client.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.LoginConfig;
import ru.liga.domain.Token;
import ru.liga.domain.UserAuth;

@Component
public class LoginClient {

    private final RestTemplate restTemplate;
    private final LoginConfig loginConfig;

    @Autowired
    public LoginClient(RestTemplate restTemplate, LoginConfig loginConfig) {
        this.restTemplate = restTemplate;
        this.loginConfig = loginConfig;
    }

    public String login(UserAuth auth) {
        HttpEntity<UserAuth> request = new HttpEntity<>(auth);
        try {
            Token token = restTemplate.postForObject(loginConfig.getLoginUrl(), request, Token.class);
            return token.getJwtToken();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                return "Wrong Credential";
            }
            return "Server error";
        }
    }
}
