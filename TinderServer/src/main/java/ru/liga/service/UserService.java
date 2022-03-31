package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.liga.domain.User;
import ru.liga.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserById(Long.parseLong(auth.getName()));
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public void updatePassword(Map.Entry<String, String> password) {
        if (!password.getKey().equals("password")) {
            throw new IllegalArgumentException("should contain password");
        } else {
            User currentUser = getCurrentUser();
            currentUser.setPassword(passwordEncoder.encode(password.getValue()));
            userRepository.save(currentUser);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Boolean isUserExists(Long id) {
        return userRepository.findById(id).isPresent();
    }
}
