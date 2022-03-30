package ru.liga.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private Long id;
    private String password;
    private Profile profile;
}
