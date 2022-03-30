package ru.liga.client;

import org.springframework.stereotype.Component;
import ru.liga.domain.Profile;
import ru.liga.domain.User;

@Component
public class ApiConsumerImpl implements ApiConsumer{
    @Override
    public void registerUser(User user) {

    }

    @Override
    public String login(Long userId) {
        return null;
    }

    @Override
    public Profile getUserProfile() {
        return null;
    }
}
