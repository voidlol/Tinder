package ru.liga.client.registration;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.UsersConfig;

import java.util.Set;

@Component
public class UserClient {

    private final UsersConfig usersConfig;
    private final RestTemplate restTemplate;

    public UserClient(UsersConfig usersConfig, RestTemplate restTemplate) {
        this.usersConfig = usersConfig;
        this.restTemplate = restTemplate;
    }

    public Set<Long> getAllUserIds() {
        Long[] userIds = restTemplate.getForObject(usersConfig.getUsersUrl(), Long[].class);
        return Set.of(userIds);
    }
}
