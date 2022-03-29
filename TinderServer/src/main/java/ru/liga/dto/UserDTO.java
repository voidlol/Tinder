package ru.liga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO {

    private String password;
    private ApplicationDTO applicationDTO;

}
