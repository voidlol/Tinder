package ru.liga.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;

@Component
public class WelcomeHandler implements InputHandler {

    private static final String LOGIN = "/login";
    private final UserDetailsCache userDetailsCache;

    @Autowired
    public WelcomeHandler(UserDetailsCache userDetailsCache) {
        this.userDetailsCache = userDetailsCache;
    }

    @Override
    public SendMessage handle(Message message) {
        return processMessage(message);
    }

    @Override
    public BotState getBotState() {
        return BotState.WELCOME;
    }

    private SendMessage processMessage(Message message) {
        String text = message.getText();
        Long id = message.getFrom().getId();
        SendMessage reply = new SendMessage();

        if (text.equals(LOGIN)) {
            userDetailsCache.changeUserState(id, BotState.LOGIN_ASK_PASSWORD);
            reply.setText("Enter password");
        } else {
            if (userDetailsCache.isRegistered(id)) {
                reply.setText("You are already registered!");
            } else {
                reply.setText("Enter password");
                userDetailsCache.changeUserState(id, BotState.REGISTER_ASK_PASSWORD);
            }
        }
        return reply;
    }
}
