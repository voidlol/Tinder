package ru.liga.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.botstate.BotState;
import ru.liga.client.registration.RegistrationClient;
import ru.liga.client.registration.UserClient;
import ru.liga.domain.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDetailsCache {
    private Map<Long, BotState> userState = new HashMap<>();
    private final UserClient userClient;
    private Map<Long, User> userAccounts = new HashMap<>();
    private final RegistrationClient registerClient;

    @Autowired
    public UserDetailsCache(UserClient userClient, RegistrationClient registerClient) {
        this.userClient = userClient;
        this.registerClient = registerClient;
    }

    public User getUser(Long id) {
        return userAccounts.getOrDefault(id, new User());
    }

    public void saveUser(User user) {
        userAccounts.put(user.getId(), user);
    }

    public boolean isRegistered(Long id) {
        return userClient.isUserExists(id);
    }

    public BotState getCurrentBotState(Long id) {
        return userState.getOrDefault(id, null);
    }

    public void registerUser(Long id) {
        registerClient.registerUser(userAccounts.get(id));
    }

    public void changeUserState(Long id, BotState state) {
        userState.put(id, state);
    }
}
