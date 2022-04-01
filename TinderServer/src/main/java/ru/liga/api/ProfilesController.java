package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.Profile;
import ru.liga.service.ProfileService;

import java.util.Set;

@RestController
@RequestMapping("/api/profile")  //http://localhost:8085/api/profile/
public class ProfilesController {

    private final ProfileService profileService;

    @Autowired
    public ProfilesController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/like/{target}")
    public void likeUser(@PathVariable Profile target) {
        profileService.likeUser(target);
    }

    @PostMapping("/unlike/{target}")
    public void unlikeUser(@PathVariable Profile target) {
        profileService.unlikeUser(target);
    }

    @GetMapping("/")
    public Profile getUserApplication() {
        return profileService.getUserApplication();
    }


    @GetMapping("/weLike")
    public Set<Profile> weLike() {
        return profileService.weLike();
    }

    @GetMapping("/isReciprocity/{target}")
    public Boolean isReciprocity(@PathVariable Profile target) {
        return profileService.isReciprocity(target);
    }

    @GetMapping("/search")
    public Set<Profile> searchList() {
        return profileService.searchList();
    }
}
