package ru.liga.client.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.User;

@RequiredArgsConstructor
@Component
public class RegistrationClient {

    @Value("${api.registrationUrl}")
    private String registrationUrl;
    private final RestTemplate restTemplate;

    public void registerUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForObject(registrationUrl, request, User.class);
    }
}
