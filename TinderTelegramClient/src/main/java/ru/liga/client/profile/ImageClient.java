package ru.liga.client.profile;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@AllArgsConstructor
public class ImageClient {

    private final RestTemplate restTemplate;

    public File getImageForProfile(Profile profile) {
        HttpEntity<Profile> httpEntity = new HttpEntity<>(profile);
        return restTemplate.postForObject("http://localhost:8085/api/image/", httpEntity, File.class);
    }
}
