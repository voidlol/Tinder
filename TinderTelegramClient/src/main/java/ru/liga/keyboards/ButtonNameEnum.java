package ru.liga.keyboards;

public enum ButtonNameEnum {
    REGISTRATION_BUTTON("Регистрация"),
    LOGIN_BUTTON("Авторизация"),
    MENU_BUTTON("Меню"),
    RIGHT_BUTTON("Вправо"),
    LEFT_BUTTON("Влево"),
    SEARCH_BUTTON("Поиск"),
    PROFILE_BUTTON("Профиль"),
    BACK_BUTTON("Назад"),
    FAVORITES_BUTTON("Избранное");

    private final String buttonName;


    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}
