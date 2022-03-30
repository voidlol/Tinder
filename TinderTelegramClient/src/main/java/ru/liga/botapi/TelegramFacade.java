package ru.liga.botapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserSessionCache;
import ru.liga.client.cache.UserDetailsCache;

@Component
public class TelegramFacade {

    private BotStateContext botStateContext;
    private UserSessionCache userSessionCache;
    private UserDetailsCache userDetailsCache;

    @Autowired
    public TelegramFacade(BotStateContext botStateContext, UserSessionCache userSessionCache, UserDetailsCache userDetailsCache) {
        this.botStateContext = botStateContext;
        this.userSessionCache = userSessionCache;
        this.userDetailsCache = userDetailsCache;
    }

    public SendMessage handleUpdate(Update update) {
        SendMessage reply = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            reply = handleMessage(message);
        }
        return reply;
    }

    private SendMessage handleMessage(Message message) {
        String inputText = message.getText();
        long userId = message.getFrom().getId();
        BotState botState = userDetailsCache.getCurrentBotState(userId);
        SendMessage reply;

        if (botState == null && inputText.equals("/start")) {
            botState = BotState.WELCOME;
        }
        return botStateContext.processInputMessage(botState, message);
    }
}
