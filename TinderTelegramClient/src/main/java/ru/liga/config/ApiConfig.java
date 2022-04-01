package ru.liga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {

    private String serverUrl;
    private String apiUrl;
    private String registrationUrl;
    private String loginUrl;
    private String likeUrl;
    private String unlikeUrl;
    private String profileUrl;
    private String usersUrl;
    private String weLike;

    @Bean
    public ProfileConfig createProfileConfig() {
        return new ProfileConfig(serverUrl, apiUrl, profileUrl, likeUrl, unlikeUrl, weLike);
    }

    @Bean
    public LoginConfig createLoginConfig() {
        return new LoginConfig(serverUrl, loginUrl);
    }

    @Bean
    public RegistrationConfig createRegistrationConfig() {
        return new RegistrationConfig(serverUrl, registrationUrl);
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UsersConfig createUsersConfig() {
        return new UsersConfig(serverUrl, apiUrl, usersUrl);
    }
}
