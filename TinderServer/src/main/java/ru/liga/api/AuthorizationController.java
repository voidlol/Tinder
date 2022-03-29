package ru.liga.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorize")
public class AuthorizationController {

    @PostMapping
    @RequestMapping("/test")
    public String authorize(@RequestHeader("userName") String userName,
                            @RequestHeader("password") String password) {
        return userName + " " + password;
    }
}

