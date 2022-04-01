package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.liga.client.cache.UserSessionCache;

@Component
public class AuthorizationService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserSessionCache userSessionCache;
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    public AuthorizationService(UserSessionCache userSessionCache) {
        this.userSessionCache = userSessionCache;
    }

    public HttpEntity<Void> getHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        String token = TOKEN_PREFIX + userSessionCache.getTokenForUser(userId);
        headers.set(AUTHORIZATION, token);
        return new HttpEntity(headers);
    }
}
