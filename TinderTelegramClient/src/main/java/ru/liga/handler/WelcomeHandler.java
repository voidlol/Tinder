package ru.liga.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.cache.UserDetailsCache;

@Component
public class WelcomeHandler implements InputHandler {

    //Юзер жмет кнопку регистрация ->

    private static final String LOGIN = "/login";
    private final UserDetailsCache userDetailsCache;

    @Autowired
    public WelcomeHandler(UserDetailsCache userDetailsCache) {
        this.userDetailsCache = userDetailsCache;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public BotState getBotState() {
        return BotState.WELCOME;
    }

    private SendMessage processMessage(Message message) {
        String text = message.getText();
        Long userId = message.getFrom().getId();
        SendMessage reply = new SendMessage();

        if (text.equals(LOGIN)) {
            userDetailsCache.changeUserState(userId, BotState.LOGIN_ASK_PASSWORD);
            reply.setText("Enter password");
        } else {
            if (userDetailsCache.isRegistered(userId)) {
                reply.setText("You are already registered!");
            } else {
                reply.setText("Enter password");
                userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_PASSWORD);
            }
        }
        return reply;
    }
}
