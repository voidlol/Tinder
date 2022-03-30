package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.domain.Application;
import ru.liga.domain.SexType;
import ru.liga.domain.User;
import ru.liga.repo.ApplicationRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserService userService) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
    }


    public void likeUser(User target) {
        User current = userService.getCurrentUser();
        current.getApplicationId().getWhomILiked().add(target.getApplicationId());
        applicationRepository.save(current.getApplicationId());
    }

    public Set<Application> whoLikedUser() {
        User current = userService.getCurrentUser();
        return current.getApplicationId().getWhoLikedMe();
    }

    public Set<Application> whomILiked() {
        User current = userService.getCurrentUser();
        return current.getApplicationId().getWhomILiked();
    }

    public Set<Application> findApplications() {
        Set<Application> applications = new HashSet<>();
        User currentUser = userService.getCurrentUser();
        Application userApplication = currentUser.getApplicationId();
        for (SexType sexType : userApplication.getLookingFor()) {
            applications.addAll(applicationRepository
                    .findApplicationBySexTypeAndLookingForContaining(sexType, userApplication.getSexType()));
        }
        applications.remove(userApplication);
        return applications;
    }
}
