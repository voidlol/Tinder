package ru.liga.client.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;

@Component
@RequiredArgsConstructor
public class TranslatorClient {

    private final RestTemplate restTemplate;
    @Value("${api.translateUrl}")
    private String translateUrl;

    public Profile getTranslatedProfile(Profile profile) {
        HttpEntity<Profile> httpEntity = new HttpEntity<>(profile);
        return restTemplate.postForObject(translateUrl, httpEntity, Profile.class);
    }
}
