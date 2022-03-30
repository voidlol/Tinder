package ru.liga.client.cache;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserSessionCache implements UserSessionCache {

    private final Map<Long, String> tokens = new HashMap<>();

    @Override
    public String getTokenForUser(Long id) {
        return tokens.getOrDefault(id, null);
    }

    @Override
    public void addTokenForUser(Long id, String token) {
        tokens.put(id, token);
    }
}
