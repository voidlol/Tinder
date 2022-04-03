package ru.liga.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.domain.Profile;
import ru.liga.domain.User;
import ru.liga.repo.ApplicationRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ProfileService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

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
        return applicationRepository.findAllValidProfilesForUser(userProfile.getId(), userProfile.getSexType().ordinal());
    }

    public void unlikeUser(Profile target) {
        User current = userService.getCurrentUser();
        current.getProfile().getWeLike().remove(target);
        applicationRepository.save(current.getProfile());
    }

    public Set<Profile> usLike() {
        User currentUser = userService.getCurrentUser();
        return currentUser.getProfile().getWhoLikedMe();
    }

    public Set<Profile> getFavorites() {
        User currentUser = userService.getCurrentUser();
        Set<Profile> weLike = currentUser.getProfile().getWeLike();
        Set<Profile> whoLikedMe = currentUser.getProfile().getWhoLikedMe();
        return Stream.concat(whoLikedMe.stream(), weLike.stream()).collect(Collectors.toSet());
    }
}
