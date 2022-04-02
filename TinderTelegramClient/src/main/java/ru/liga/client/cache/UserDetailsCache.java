package ru.liga.client.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.liga.botstate.BotState;
import ru.liga.client.registration.UserClient;
import ru.liga.domain.ScrollingWrapper;
import ru.liga.domain.User;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class UserDetailsCache {

    private final Map<Long, BotState> userState = new HashMap<>();
    private final UserClient userClient;
    private final Map<Long, User> userAccounts = new HashMap<>();
    private final Map<Long, ScrollingWrapper> userScrollers = new HashMap<>();

    public void addScroller(Long userId, ScrollingWrapper scrollingWrapper) {
        userScrollers.put(userId, scrollingWrapper);
    }

    public ScrollingWrapper getScroller(Long userId) {
        return userScrollers.get(userId);
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

    public void changeUserState(Long id, BotState state) {
        userState.put(id, state);
    }
}
