package ru.liga.client.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;
import ru.liga.service.AuthorizationService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProfileRestClient implements ProfileClient {

    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;
    @Value("${api.profileUrl}")
    private String profileUrl;
    private static final String SEARCH_URL = "search";
    private static final String RECIPROCITY_URL = "isReciprocity/%d";
    private static final String RELATION_URL = "%d/relation";
    private static final String FAVORITES_URL = "favorites";
    private static final String LIKE_URL = "like/%d";
    private static final String DISLIKE_URL = "unlike/%d";

    @Override
    public Profile getUserProfile(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        ResponseEntity<Profile> exchange = restTemplate.exchange(profileUrl, HttpMethod.GET, requestEntity, Profile.class);
        return exchange.getBody();
    }

    @Override
    public List<Profile> getValidProfiles(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        ResponseEntity<Profile[]> exchange = restTemplate.exchange(profileUrl + SEARCH_URL, HttpMethod.GET, requestEntity, Profile[].class);
        return List.of(exchange.getBody());
    }

    @Override
    public boolean likeProfile(Long userId, Long profileId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        String likeUrl = profileUrl + LIKE_URL;
        String reciprocityUrl = profileUrl + RECIPROCITY_URL;
        restTemplate.postForObject(String.format(likeUrl, profileId), requestEntity, String.class);
        return restTemplate.exchange(String.format(reciprocityUrl, profileId), HttpMethod.GET, requestEntity, Boolean.class).getBody();
    }

    @Override
    public List<Profile> getFavorites(Long userId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        ResponseEntity<Profile[]> favorites = restTemplate.exchange(profileUrl + FAVORITES_URL, HttpMethod.GET, requestEntity, Profile[].class);
        return List.of(favorites.getBody());
    }

    @Override
    public void unlikeProfile(Long userId, Long profileId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        restTemplate.postForObject(profileUrl + String.format(DISLIKE_URL, profileId), requestEntity, String.class);
    }

    @Override
    public String getRelation(Long userId, Long profileId) {
        HttpEntity<Void> requestEntity = authorizationService.getEntityWithAuthorizationHeader(userId);
        String relationUrl = profileUrl + RELATION_URL;
        ResponseEntity<String> exchange = restTemplate.exchange(String.format(relationUrl, profileId), HttpMethod.GET, requestEntity, String.class);
        return exchange.getBody();
    }
}
