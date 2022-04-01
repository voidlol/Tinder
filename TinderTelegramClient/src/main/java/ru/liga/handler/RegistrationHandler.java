package ru.liga.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.domain.Profile;
import ru.liga.domain.User;

@Component
public class RegistrationHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private static final String NOT_MATHCING_PASSWORDS = "Пароли не совпадают!\nПожалуйста, введите пароль заново:";

    @Autowired
    public RegistrationHandler(UserDetailsCache userDetailsCache) {
        this.userDetailsCache = userDetailsCache;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public BotState getBotState() {
        return BotState.REGISTER;
    }

    private SendMessage processMessage(Message message) {
        Long userId = message.getFrom().getId();
        BotState currentBotState = userDetailsCache.getCurrentBotState(userId);
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());
        User newUser = userDetailsCache.getUser(userId);

        switch (currentBotState) {
            case REGISTER_ASK_PASSWORD:
                newUser.setId(userId);
                newUser.setPassword(message.getText());
                userDetailsCache.saveUser(newUser);
                reply.setText(BotState.REGISTER_ASK_CONF_PASSWORD.getMessage());
                userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_CONF_PASSWORD);
                break;
            case REGISTER_ASK_CONF_PASSWORD:
                String oldPassword = newUser.getPassword();
                String currentPassword = message.getText();
                if (!oldPassword.equals(currentPassword)) {
                    reply.setText(NOT_MATHCING_PASSWORDS);
                    userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_PASSWORD);
                } else {
                    newUser.setProfile(new Profile());
                    reply.setText(BotState.PROFILE_FILLING_ASK_NAME.getMessage());
                    userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_NAME);
                }

                break;
        }
        return reply;
    }
}
