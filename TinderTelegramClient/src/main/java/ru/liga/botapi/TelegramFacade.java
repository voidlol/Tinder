package ru.liga.botapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserSessionCache;
import ru.liga.client.cache.UserDetailsCache;

@Component
public class TelegramFacade {

    private final BotStateContext botStateContext;
    private final UserSessionCache userSessionCache;
    private final UserDetailsCache userDetailsCache;

    @Autowired
    public TelegramFacade(BotStateContext botStateContext, UserSessionCache userSessionCache, UserDetailsCache userDetailsCache) {
        this.botStateContext = botStateContext;
        this.userSessionCache = userSessionCache;
        this.userDetailsCache = userDetailsCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> reply = null;

        if (update.hasCallbackQuery()) {
            reply = handleCallBack(update.getCallbackQuery());
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            reply = handleMessage(message);
        }
        return reply;
    }

    private BotApiMethod<?> handleMessage(Message message) {
        String inputText = message.getText();
        System.out.println(inputText + " " + message.getFrom());
        long userId = message.getFrom().getId();
        BotState botState = userDetailsCache.getCurrentBotState(userId);

        if (botState == null && inputText.equals("/start")) {
            botState = BotState.ROOT_MENU;
        }
        return botStateContext.processInputMessage(botState, message);
    }

    private BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        BotState userBotState = userDetailsCache.getCurrentBotState(callbackQuery.getFrom().getId());
        return botStateContext.processCallBack(userBotState, callbackQuery);
    }
}
