package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.domain.Application;
import ru.liga.domain.User;
import ru.liga.repo.ApplicationRepository;
import ru.liga.repo.UserRepository;

import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }


    public void likeUser(User current, User target) {
        current.getApplicationId().getWhomILiked().add(target.getApplicationId());
        applicationRepository.save(current.getApplicationId());
    }

    public Set<Application> whoLikedUser(String username) {
        User current = userRepository.getUserById(Long.parseLong(username));
        return current.getApplicationId().getWhoLikedMe();
    }
}
