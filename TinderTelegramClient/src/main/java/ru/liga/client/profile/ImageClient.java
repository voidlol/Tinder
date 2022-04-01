package ru.liga.client.profile;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.Profile;

import java.io.*;

@Component
@AllArgsConstructor
public class ImageClient {

    private final RestTemplate restTemplate;

    public File getImageForProfile(Profile profile) {
        HttpEntity<Profile> httpEntity = new HttpEntity<>(profile);
        byte[] bytes = restTemplate.postForObject("http://localhost:8085/api/image", httpEntity, byte[].class);
        File file = new File("/tmp/" + profile.getName() + ".jpg");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
