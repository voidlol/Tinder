package ru.liga.domain;

import lombok.Getter;

@Getter
public enum SexType {
    MALE("Сударь"),
    FEMALE("Сударыня");

    private final String translatedName;

    SexType(String translatedName) {
        this.translatedName = translatedName;
    }
}
