package ru.liga.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
public class Profile {

    @ToString.Exclude
    private Long id;
    private String name;
    private String description;
    private SexType sexType;
    @ToString.Exclude
    private Set<SexType> lookingFor;


    public String getCaption() {
        return this.getSexType().getTranslatedName() + ", " + this.getName();
    }
}
