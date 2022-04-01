package ru.liga.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Token {

    private String jwtToken;
    private String message;

}
