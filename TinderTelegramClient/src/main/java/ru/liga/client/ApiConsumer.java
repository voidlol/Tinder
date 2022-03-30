package ru.liga.client;

import ru.liga.domain.Profile;
import ru.liga.domain.User;

public interface ApiConsumer {

    void registerUser(User user);
    String login(Long userId);
    Profile getUserProfile();
}
