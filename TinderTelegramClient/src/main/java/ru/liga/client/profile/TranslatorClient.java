package ru.liga.client.profile;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;
import ru.liga.service.AuthorizationService;

@Component
public class TranslatorClient {

    private final RestTemplate restTemplate;
    private final AuthorizationService authorizationService;

    public TranslatorClient(RestTemplate restTemplate, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.authorizationService = authorizationService;
    }

    public Profile getTranslatedProfile(Long userId, Profile profile) {
        HttpEntity<Void> headers = authorizationService.getHeaders(userId);
        HttpEntity<Profile> httpEntity = new HttpEntity<>(profile, headers.getHeaders());
        return restTemplate.postForObject("http://localhost:8085/api/translate/profile", httpEntity, Profile.class);
    }
}
