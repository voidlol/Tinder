package ru.liga.client.profile;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;
import ru.liga.service.AuthorizationService;

@Component
public class TranslatorClient {

    private final RestTemplate restTemplate;

    public TranslatorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Profile getTranslatedProfile(Profile profile) {
        HttpEntity<Profile> httpEntity = new HttpEntity<>(profile);
        return restTemplate.postForObject("http://localhost:8085/api/translate/profile", httpEntity, Profile.class);
    }
}
