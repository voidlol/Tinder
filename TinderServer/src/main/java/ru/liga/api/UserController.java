package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.User;
import ru.liga.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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

}
