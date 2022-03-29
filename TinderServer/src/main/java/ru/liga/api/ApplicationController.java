package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/{current}/like/{target}")
    public void likeUser(@PathVariable User current,
                         @PathVariable User target) {
        applicationService.likeUser(current, target);
    }

    @GetMapping("/whoLikedUs")
    public Set<Application> whoLikedUs() {
        return applicationService.whoLikedUser(getCurrentUsername());
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
