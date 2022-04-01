package ru.liga.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.liga.client.cache.UserSessionCache;

@AllArgsConstructor
@Component
public class AuthorizationService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserSessionCache userSessionCache;
    private static final String AUTHORIZATION = "Authorization";

    public HttpEntity<Void> getHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        String token = TOKEN_PREFIX + userSessionCache.getTokenForUser(userId);
        headers.set(AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
