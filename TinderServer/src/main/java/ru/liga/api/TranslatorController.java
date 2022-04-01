package ru.liga.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.domain.Profile;
import ru.liga.service.PrerevolutionaryTranslator;

@RestController
@AllArgsConstructor
@RequestMapping("/api/translate")
public class TranslatorController {

    private final PrerevolutionaryTranslator prerevolutionaryTranslator;


    @PostMapping("/profile")
    public Profile translate(@RequestBody Profile userProfile) {
        return prerevolutionaryTranslator.translateProfile(userProfile);
    }
}
