package ru.liga.botstate;

import lombok.Getter;

@Getter
public enum BotState {
    WELCOME("\uD83D\uDC4B Привет, я TinderBot!\n" +
            "Рад привествовать тебя на нашем сервисе знакомств Tinder.\n\n" +
            "Здесь ты сможешь:\n" +
            "✅ найти свою вторую половинку;" +
            "✅ познакомиться и завести общение с приятными и инетерсными людьми.\n" +
            "Удачи!\n\n" +
            "Для начала, тебе необходимо зарегистрироваться или авторизоваться на нашем сервисе.\n" +
            "Пожалуйста, воспользуйся клавиатурой, чтобы начать работу\uD83D\uDC47"),
    REGISTER("Регистрация"),
    REGISTERED("Вы зарегистрированы!"),
    REGISTER_ASK_PASSWORD("Введите пароль: "),
    REGISTER_ASK_CONF_PASSWORD("Подтвердите пароль: "),
    PROFILE_FILLING("Заполнение профиля"),
    PROFILE_FILLING_ASK_NAME("Введите Ваше имя: "),
    PROFILE_FILLING_ASK_GENDER("Ваш пол: "),
    PROFILE_FILLING_ASK_DESCRIPTION("Расскажите о себе"),
    PROFILE_FILLING_ASK_LOOKING_FOR("Кого Вы ищете?"),
    LOGIN("Авторизация"),
    LOGIN_ASK_PASSWORD("Введите пароль: "),
    IN_MENU("Меню"),
    SEARCHING("Поиск"),
    ROOT_MENU,
    VIEWING("Просмотр");

    private String message;

    BotState(String message) {
        this.message = message;
    }

    BotState() {

    }
}
