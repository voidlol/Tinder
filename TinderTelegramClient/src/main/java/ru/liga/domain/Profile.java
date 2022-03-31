package ru.liga.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class Profile {

    private Long id;
    private String name;
    private String description;
    private SexType sexType;
    private Set<SexType> lookingFor;

}
