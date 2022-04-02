package ru.liga.client.profile;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.config.ProfileConfig;
import ru.liga.domain.Profile;
import ru.liga.service.AuthorizationService;

import java.util.List;

@AllArgsConstructor
@Component
public class ProfileClientImpl implements ProfileClient {

    private final RestTemplate restTemplate;
    private final ProfileConfig profileConfig;
    private final AuthorizationService authorizationService;

    @Override
    public Profile getUserProfile(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        ResponseEntity<Profile> exchange = restTemplate.exchange(profileConfig.getProfileUrl(), HttpMethod.GET, requestEntity, Profile.class);
        return exchange.getBody();
    }

    @Override
    public List<Profile> getValidProfiles(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        ResponseEntity<Profile[]> exchange = restTemplate.exchange(profileConfig.getProfileUrl() + "/search", HttpMethod.GET, requestEntity, Profile[].class);
        return List.of(exchange.getBody());
    }

    @Override
    public boolean likeProfile(Long userId, Long profileId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        restTemplate.postForObject(String.format(profileConfig.getLikeUrl(), profileId), requestEntity, String.class);
        return restTemplate.exchange(String.format(profileConfig.getProfileUrl() + "/isReciprocity/%d", profileId), HttpMethod.GET, requestEntity, Boolean.class).getBody();
    }

    @Override
    public List<Profile> getFavorites(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        ResponseEntity<Profile[]> exchange = restTemplate.exchange(profileConfig.getWeLikeUrl(), HttpMethod.GET, requestEntity, Profile[].class);
        return List.of(exchange.getBody());
    }

    @Override
    public void unlikeProfile(Long userId, Long profileId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        restTemplate.postForObject(String.format(profileConfig.getUnlikeUrl(), profileId), requestEntity, String.class);
    }
}
