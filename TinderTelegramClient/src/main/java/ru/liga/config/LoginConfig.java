package ru.liga.config;

import lombok.Getter;

@Getter
public class LoginConfig {

    private final String loginUrl;

    public LoginConfig(String serverUrl, String loginUrl) {
        this.loginUrl = serverUrl + loginUrl;
    }
}
