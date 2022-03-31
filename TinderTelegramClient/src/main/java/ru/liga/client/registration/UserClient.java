package ru.liga.client.registration;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.UsersConfig;

@Component
public class UserClient {

    private final UsersConfig usersConfig;
    private final RestTemplate restTemplate;

    public UserClient(UsersConfig usersConfig, RestTemplate restTemplate) {
        this.usersConfig = usersConfig;
        this.restTemplate = restTemplate;
    }

    public boolean isUserExists(Long id) {
        Boolean forObject = restTemplate.getForObject(String.format(usersConfig.getUsersUrl(), id), Boolean.class);
        return forObject;
    }

}
