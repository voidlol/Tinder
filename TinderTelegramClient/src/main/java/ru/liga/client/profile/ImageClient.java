package ru.liga.client.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class ImageClient {

    private final RestTemplate restTemplate;
    @Value("${api.imageUrl}")
    private String imageUrl;

    public File getImageForProfile(Profile profile) {
        HttpEntity<Profile> httpEntity = new HttpEntity<>(profile);
        return restTemplate.postForObject(imageUrl, httpEntity, File.class);
    }
}
