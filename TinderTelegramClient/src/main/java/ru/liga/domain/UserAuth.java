package ru.liga.domain;

import lombok.Data;

@Data
public class UserAuth {

    private String username;
    private String password;

    public UserAuth(Long id, String password) {
        this.username = String.valueOf(id);
        this.password = password;
    }
}
