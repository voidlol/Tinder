package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.domain.Application;
import ru.liga.service.PrerevolutionaryTranslator;

@RestController
@RequestMapping("/api/translate")
public class TranslatorController {

    private final PrerevolutionaryTranslator prerevolutionaryTranslator;

    @Autowired
    public TranslatorController(PrerevolutionaryTranslator prerevolutionaryTranslator) {
        this.prerevolutionaryTranslator = prerevolutionaryTranslator;
    }

    @PostMapping("/application")
    public Application translate(@RequestBody Application application) {
        return application;
        //return prerevolutionaryTranslator.translate(application);
    }
}
