package ru.liga.client.login;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.LoginConfig;
import ru.liga.domain.Token;
import ru.liga.domain.UserAuth;

@AllArgsConstructor
@Component
public class LoginClient {

    private final RestTemplate restTemplate;
    private final LoginConfig loginConfig;

    public String login(UserAuth auth) {
        HttpEntity<UserAuth> request = new HttpEntity<>(auth);
        try {
            Token token = restTemplate.postForObject(loginConfig.getLoginUrl(), request, Token.class);
            return token.getJwtToken();
        } catch (HttpClientErrorException e) {
            return "Wrong password";
        }
    }
}
