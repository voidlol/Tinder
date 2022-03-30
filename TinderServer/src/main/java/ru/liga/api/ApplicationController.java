package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.Application;
import ru.liga.domain.User;
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
    public void likeUser(@PathVariable User target) {
        applicationService.likeUser(target);
    }

    @GetMapping("/whoLikedUs")
    public Set<Application> whoLikedUs() {
        return applicationService.whoLikedUser();
    }

    @GetMapping("/whomILiked")
    public Set<Application> whomIliked() {
        return applicationService.whomILiked();
    }

    @GetMapping("/findApplications")
    public Set<Application> findApplications() {
        return applicationService.findApplications();
    }
}
