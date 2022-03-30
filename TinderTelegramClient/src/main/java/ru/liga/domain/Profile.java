package ru.liga.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Profile {

    private String name;
    private String description;
    private SexType gender;
    private Set<SexType> lookingFor;
}
