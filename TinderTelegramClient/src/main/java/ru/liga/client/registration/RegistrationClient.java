package ru.liga.client.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.RegistrationConfig;
import ru.liga.domain.User;

@AllArgsConstructor
@Component
public class RegistrationClient {

    private final RegistrationConfig registrationConfig;
    private final RestTemplate restTemplate;

    public void registerUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForObject(registrationConfig.getRegistrationUrl(), request, User.class);
    }
}
