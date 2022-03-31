package ru.liga.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.cache.UserDetailsCache;

@Component
public class RootMenuHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;

    public RootMenuHandler(UserDetailsCache userDetailsCache) {
        this.userDetailsCache = userDetailsCache;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processMessage(message);
    }

    private SendMessage processMessage(Message message) {
        Long userId = message.getFrom().getId();
        SendMessage reply = new SendMessage();
        reply.setText(getBotState().getMessage());
        //reply.setReplyMarkup();
        userDetailsCache.changeUserState(userId, BotState.WELCOME);
        return reply;
    }

    @Override
    public BotState getBotState() {
        return BotState.ROOT_MENU;
    }
}
