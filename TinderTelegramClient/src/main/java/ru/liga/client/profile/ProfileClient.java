package ru.liga.client.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.ProfileConfig;
import ru.liga.domain.Profile;
import ru.liga.service.AuthorizationService;

import java.util.Set;

@Component
public class ProfileClient {

    private final RestTemplate restTemplate;
    private final ProfileConfig profileConfig;
    private final AuthorizationService authorizationService;

    @Autowired
    public ProfileClient(RestTemplate restTemplate, ProfileConfig profileConfig, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.profileConfig = profileConfig;
        this.authorizationService = authorizationService;
    }

    public Profile getUserProfile(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getHeaders(userId);
        ResponseEntity<Profile> exchange = restTemplate.exchange(profileConfig.getProfileUrl(), HttpMethod.GET, requestEntity, Profile.class);
        return exchange.getBody();
    }

    public void likeProfile(Long userId, Long profileId) {
        HttpEntity<Void> requestEntity = authorizationService.getHeaders(userId);
        restTemplate.postForObject(String.format(profileConfig.getLikeUrl(), profileId), requestEntity, String.class);
    }

    public Set<Profile> getFavorites(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getHeaders(userId);
        ResponseEntity<Profile[]> exchange = restTemplate.exchange("http://localhost:8085/api/applications/weLike", HttpMethod.GET, requestEntity, Profile[].class);
        return Set.of(exchange.getBody());
    }
}
