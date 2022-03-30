package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ru.liga.cache.UserSessionCache;

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
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjQ4NTY3NjA3fQ.7Hk8uI0EWuJCJl48sQAuc6M0WBwTbh-YoXy4iWNve5a80hpCaJqhfGAMk2ENlQXDcSThWlwsYAf-oo8nmULgUA";
        headers.set(AUTHORIZATION, token);
        return new HttpEntity(headers);
    }
}
