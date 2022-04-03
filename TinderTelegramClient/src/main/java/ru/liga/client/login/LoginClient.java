package ru.liga.client.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Token;
import ru.liga.domain.UserAuth;

@RequiredArgsConstructor
@Component
public class LoginClient {

    private final RestTemplate restTemplate;
    @Value("${api.loginUrl}")
    private String loginUrl;

    public String login(UserAuth auth) {
        HttpEntity<UserAuth> request = new HttpEntity<>(auth);
        try {
            Token token = restTemplate.postForObject(loginUrl, request, Token.class);
            return token.getJwtToken();
        } catch (HttpClientErrorException e) {
            return "Wrong password";
        }
    }
}
