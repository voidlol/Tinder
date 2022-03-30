package ru.liga.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.liga.botstate.BotState;
import ru.liga.client.ApiConsumer;
import ru.liga.domain.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class UserDetailsCache {
    private Map<Long, BotState> userState = new HashMap<>();
    private Set<Long> registeredUsers = new HashSet<>();
    private Map<Long, User> userAccounts = new HashMap<>();
    private final ApiConsumer apiConsumer;

    @Autowired
    public UserDetailsCache(ApiConsumer apiConsumer) {
        this.apiConsumer = apiConsumer;
    }

    public User getUser(Long id) {
        return userAccounts.getOrDefault(id, new User());
    }

    public void saveUser(User user) {
        userAccounts.put(user.getId(), user);
    }

    public boolean isRegistered(Long id) {
        return registeredUsers.contains(id);
    }

    public BotState getCurrentBotState(Long id) {
        return userState.getOrDefault(id, null);
    }

    public void registerUser(Long id) {
        apiConsumer.registerUser(userAccounts.get(id));
        registeredUsers.add(id);
    }

    public void changeUserState(Long id, BotState state) {
        userState.put(id, state);
    }
}
