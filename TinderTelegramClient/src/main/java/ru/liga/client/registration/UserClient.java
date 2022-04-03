package ru.liga.client.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;
    @Value("${api.usersUrl}")
    private String usersUrl;

    public boolean isUserExists(Long id) {
        return restTemplate.getForObject(String.format(usersUrl, id), Boolean.class);
    }

}
