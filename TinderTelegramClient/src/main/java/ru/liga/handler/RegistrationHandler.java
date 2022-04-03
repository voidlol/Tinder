package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.domain.Profile;
import ru.liga.domain.User;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class RegistrationHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private static final String NOT_MATCHING_PASSWORDS = "Password doesnt match!\nEnter new password:";

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        return Collections.emptyList();
    }

    @Override
    public BotState getBotState() {
        return BotState.REGISTER;
    }

    private List<PartialBotApiMethod<?>> processMessage(Message message) {
        Long userId = message.getFrom().getId();
        BotState currentBotState = userDetailsCache.getCurrentBotState(userId);
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());
        User newUser = userDetailsCache.getUser(userId);

        if (currentBotState == BotState.REGISTER_ASK_PASSWORD) {
            newUser.setId(userId);
            newUser.setPassword(message.getText());
            userDetailsCache.saveUser(newUser);
            reply.setText(BotState.REGISTER_ASK_CONF_PASSWORD.getMessage());
            userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_CONF_PASSWORD);
        } else if (currentBotState == BotState.REGISTER_ASK_CONF_PASSWORD) {
            String oldPassword = newUser.getPassword();
            String currentPassword = message.getText();
            if (!oldPassword.equals(currentPassword)) {
                reply.setText(NOT_MATCHING_PASSWORDS);
                userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_PASSWORD);
            } else {
                newUser.setProfile(new Profile());
                reply.setText(BotState.PROFILE_FILLING_ASK_NAME.getMessage());
                userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_NAME);
            }
        }
        userDetailsCache.addMessageToDelete(userId, message.getMessageId());
        return Collections.singletonList(reply);
    }
}
