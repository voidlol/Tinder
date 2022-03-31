package ru.liga.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.keyboards.KeyboardService;

@Component
public class RootMenuHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;

    public RootMenuHandler(UserDetailsCache userDetailsCache, KeyboardService keyboardService) {
        this.userDetailsCache = userDetailsCache;
        this.keyboardService = keyboardService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }

    private SendMessage processMessage(Message message) {
        Long userId = message.getFrom().getId();
        SendMessage reply = new SendMessage();
        reply.setReplyMarkup(keyboardService.getWelcomeKeyboard());
        reply.setChatId(message.getChatId().toString());
        reply.setText(BotState.WELCOME.getMessage());
        userDetailsCache.changeUserState(userId, BotState.WELCOME);
        return reply;
    }

    @Override
    public BotState getBotState() {
        return BotState.ROOT_MENU;
    }
}
