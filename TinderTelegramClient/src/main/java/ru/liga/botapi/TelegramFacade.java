package ru.liga.botapi;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.cache.UserSessionCache;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class TelegramFacade {

    private final BotStateContext botStateContext;
    private final UserSessionCache userSessionCache;
    private final UserDetailsCache userDetailsCache;

    public List<PartialBotApiMethod<?>> handleUpdate(Update update) {
        List<PartialBotApiMethod<?>> reply = new ArrayList<>();

        if (update.hasCallbackQuery()) {
            reply = handleCallBack(update.getCallbackQuery());
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            reply = handleMessage(message);
        }
        return reply;
    }

    private List<PartialBotApiMethod<?>> handleMessage(Message message) {
        String inputText = message.getText();
        long userId = message.getFrom().getId();
        BotState botState = userDetailsCache.getCurrentBotState(userId);
        System.out.println(inputText + " " + botState);

        if (botState == null && inputText.equals("/start")) {
            botState = BotState.ROOT_MENU;
        }
        return botStateContext.processInputMessage(botState, message);
    }

    private List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        BotState userBotState = userDetailsCache.getCurrentBotState(callbackQuery.getFrom().getId());
        return botStateContext.processCallBack(userBotState, callbackQuery);
    }

}
