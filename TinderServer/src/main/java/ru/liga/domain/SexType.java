package ru.liga.domain;

import lombok.Getter;

@Getter
public enum SexType {
    MALE("Сударь"), FEMALE("Сударыня");

    private String name;

    SexType(String name) {
        this.name = name;
    }
}
