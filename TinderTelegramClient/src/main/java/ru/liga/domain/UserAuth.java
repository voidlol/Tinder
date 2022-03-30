package ru.liga.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class UserAuth {

    private String username;
    private String password;

    public UserAuth(User user) {
        this.username = String.valueOf(user.getId());
        this.password = user.getPassword();
    }
}
