package ru.liga.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@ToString
public class Profile {

    @ToString.Exclude
    private Long id;
    private String name;
    private String description;
    private SexType sexType;
    @ToString.Exclude
    private Set<SexType> lookingFor;

}
