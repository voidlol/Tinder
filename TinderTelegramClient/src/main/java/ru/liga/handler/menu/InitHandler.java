package ru.liga.handler.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.handler.InputHandler;
import ru.liga.service.BotMethodService;
import ru.liga.service.TextMessageService;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitHandler implements InputHandler {

    private final BotMethodService botMethodService;
    private final TextMessageService textMessageService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return List.of(botMethodService.getDeleteMethod(message.getChatId(), message.getMessageId()),
                botMethodService.getSendMessage(message.getChatId(), textMessageService.getText("reply.start")));
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        return Collections.emptyList();
    }

    @Override
    public BotState getBotState() {
        return BotState.INIT;
    }
}
