package ru.liga.config;

import lombok.Getter;

@Getter
public class RegistrationConfig {

    private final String registrationUrl;

    public RegistrationConfig(String serverUrl, String registrationUrl) {
        this.registrationUrl = serverUrl + registrationUrl;
    }
}
