package ru.liga.cache;

public interface UserSessionCache {

    String getTokenForUser(Long id);
    void addTokenForUser(Long id, String token);
}
