package ru.liga.config;

import lombok.Getter;

@Getter
public class UsersConfig {

    private final String usersUrl;

    public UsersConfig(String serverUrl, String apiUrl, String usersUrl) {
        this.usersUrl = serverUrl + apiUrl + usersUrl;
    }
}
