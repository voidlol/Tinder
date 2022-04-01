package ru.liga.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.domain.User;
import ru.liga.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;


    @PostMapping()
    public void register(@RequestBody User user) {
        userService.add(user);
    }
}

