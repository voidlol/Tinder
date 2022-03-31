package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.Profile;
import ru.liga.service.ApplicationService;

import java.util.Set;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/like/{target}")
    public void likeUser(@PathVariable Profile target) {
        applicationService.likeUser(target);
    }

    @GetMapping("/")
    public Profile getUserApplication() {
        return applicationService.getUserApplication();
    }


    @GetMapping("/weLike")
    public Set<Profile> weLike() {
        return applicationService.weLike();
    }

    @GetMapping("/isReciprocity/{target}")
    public Boolean isReciprocity(@PathVariable Profile target) {
        return applicationService.isReciprocity(target);
    }

    @GetMapping("/search")
    public Set<Profile> searchList() {
        return applicationService.searchList();
    }
}
