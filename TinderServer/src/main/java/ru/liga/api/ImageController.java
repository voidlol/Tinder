package ru.liga.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.domain.Profile;
import ru.liga.service.ImageService;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/")
    public File getImageForProfile(@RequestBody Profile profile) {
        return imageService.getFile(profile);
    }
}
