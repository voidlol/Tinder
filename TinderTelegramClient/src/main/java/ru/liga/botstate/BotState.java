package ru.liga.botstate;

import lombok.Getter;

@Getter
public enum BotState {
    WELCOME,
    REGISTER,
    REGISTER_ASK_PASSWORD("Enter password"),
    REGISTER_ASK_CONF_PASSWORD("Confirm password"),
    PROFILE_FILLING,
    PROFILE_FILLING_ASK_NAME("Enter your name:"),
    PROFILE_FILLING_ASK_GENDER,
    PROFILE_FILLING_ASK_DESCRIPTION,
    PROFILE_FILLING_ASK_LOOKING_FOR,
    LOGIN,
    LOGIN_ASK_PASSWORD("Enter password"),
    IN_MENU,
    SEARCHING,
    VIEWING;

    private String message;

    BotState(String message) {
        this.message = message;
    }

    BotState() {

    }
}
