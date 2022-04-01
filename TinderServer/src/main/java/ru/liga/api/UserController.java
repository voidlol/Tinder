package ru.liga.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.User;
import ru.liga.service.UserService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @RequestMapping("/")
    public void addUser(@RequestBody User user) {
        userService.add(user);
    }

    @PostMapping("/changePassword")
    public void changePassword(@RequestBody Map.Entry<String, String> password) {
        userService.updatePassword(password);
    }

    @GetMapping("/all")
    public Set<Long> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

    }

    @GetMapping("/exists/{id}")
    public Boolean isUserExists(@PathVariable Long id) {
        return userService.isUserExists(id);
    }
}
