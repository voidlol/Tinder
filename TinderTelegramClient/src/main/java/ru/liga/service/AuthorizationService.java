package ru.liga.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.liga.client.cache.UserSessionCache;

@AllArgsConstructor
@Service
public class AuthorizationService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserSessionCache userSessionCache;
    private static final String AUTHORIZATION = "Authorization";

    public HttpEntity<Void> getEntityWithAuthorizationHeader(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        String token = TOKEN_PREFIX + userSessionCache.getTokenForUser(userId);
        headers.set(AUTHORIZATION, token);
        return new HttpEntity<>(headers);
    }
}
