package ru.liga.client.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.RegistrationConfig;
import ru.liga.domain.User;

@Component
public class RegistrationClient {

    private final RegistrationConfig registrationConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public RegistrationClient(RegistrationConfig registrationConfig, RestTemplate restTemplate) {
        this.registrationConfig = registrationConfig;
        this.restTemplate = restTemplate;
    }

    public void registerUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        System.out.println(registrationConfig.getRegistrationUrl());
        restTemplate.postForObject(registrationConfig.getRegistrationUrl(), request, User.class);
    }
}
