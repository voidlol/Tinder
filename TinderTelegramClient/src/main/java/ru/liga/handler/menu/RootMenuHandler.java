package ru.liga.handler.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.handler.InputHandler;
import ru.liga.service.KeyboardService;
import ru.liga.service.TextMessageService;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RootMenuHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;
    private final TextMessageService textMessageService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        return Collections.emptyList();
    }

    private List<PartialBotApiMethod<?>> processMessage(Message message) {
        Long userId = message.getFrom().getId();
        SendMessage reply = new SendMessage();
        reply.setReplyMarkup(keyboardService.getWelcomeKeyboard());
        reply.setChatId(message.getChatId().toString());
        reply.setText(textMessageService.getText("reply.welcome"));
        userDetailsCache.changeUserState(userId, BotState.WELCOME);
        return Collections.singletonList(reply);
    }

    @Override
    public BotState getBotState() {
        return BotState.ROOT_MENU;
    }
}
