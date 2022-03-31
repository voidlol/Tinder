package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.domain.Profile;
import ru.liga.domain.User;
import ru.liga.repo.ApplicationRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    @Autowired
    public ProfileService(ApplicationRepository applicationRepository, UserService userService) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
    }

    public void likeUser(Profile target) {
        User current = userService.getCurrentUser();
        current.getProfile().getWeLike().add(target);
        applicationRepository.save(current.getProfile());
    }


    public Set<Profile> weLike() {
        User current = userService.getCurrentUser();
        return current.getProfile().getWeLike();
    }

    public Profile getUserApplication() {
        return userService.getCurrentUser().getProfile();
    }

    public Boolean isReciprocity(Profile target) {
        User currentUser = userService.getCurrentUser();
        Profile profile = currentUser.getProfile();
        return profile.getWhoLikedMe().contains(target);
    }

    public Set<Profile> searchList() {
        User currentUser = userService.getCurrentUser();
        Profile userProfile = currentUser.getProfile();
        Set<Profile> weLike = userProfile.getWeLike();
        Set<Profile> collect = applicationRepository.findAll().stream()
                .filter(p -> !p.equals(userProfile))
                .filter(p -> userProfile.getLookingFor().contains(p.getSexType()))
                .filter(p -> p.getLookingFor().contains(userProfile.getSexType()))
                .filter(p -> !weLike.contains(p))
                .collect(Collectors.toSet());
        return collect;
    }

    public void unlikeUser(Profile target) {
        User current = userService.getCurrentUser();
        current.getProfile().getWeLike().remove(target);
        applicationRepository.save(current.getProfile());
    }
}
