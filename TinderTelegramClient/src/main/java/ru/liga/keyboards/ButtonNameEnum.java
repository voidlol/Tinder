package ru.liga.keyboards;

public enum ButtonNameEnum {
    REGISTRATION_BUTTON("Регистрация"),
    LOGIN_BUTTON("Авторизация"),
    MENU_BUTTON("Меню"),
    SEARCH_BUTTON("Поиск"),
    PROFILE_BUTTON("Профиль"),
    BACK_BUTTON("Назад"),
    FAVORITES_BUTTON("Избранные");

    private final String buttonName;


    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
