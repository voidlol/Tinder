package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.service.BotMethodService;
import ru.liga.service.KeyboardService;
import ru.liga.service.TextMessageService;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class WelcomeHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;
    private final TextMessageService textMessageService;
    private final BotMethodService botMethodService;

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
        return BotState.WELCOME;
    }

    private List<PartialBotApiMethod<?>> processMessage(Message message) {
        String text = message.getText();
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();

        if (text.equals(textMessageService.getText("button.login"))) {
            userDetailsCache.addMessageToDelete(userId, message.getMessageId());
            if (!userDetailsCache.isRegistered(userId)) {
                return Collections.singletonList(botMethodService.getSendMessage(
                        chatId,
                        textMessageService.getText("reply.noAccount"),
                        keyboardService.getWelcomeKeyboard()));
            } else {
                userDetailsCache.changeUserState(userId, BotState.LOGIN_ASK_PASSWORD);
                return Collections.singletonList(botMethodService.getSendMessage(
                        chatId,
                        textMessageService.getText("reply.askPassword")));
            }
        } else if (text.equals(textMessageService.getText("button.registration"))) {
            userDetailsCache.addMessageToDelete(userId, message.getMessageId());
            if (userDetailsCache.isRegistered(userId)) {
                return Collections.singletonList(botMethodService.getSendMessage(
                        chatId,
                        textMessageService.getText("reply.alreadyRegistered"),
                        keyboardService.getWelcomeKeyboard()));
            } else {
                userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_PASSWORD);
                return Collections.singletonList(botMethodService.getSendMessage(
                        chatId,
                        textMessageService.getText("reply.askPassword")));
            }
        } else {
            return Collections.singletonList(botMethodService.getDeleteMethod(chatId, message.getMessageId()));
        }
    }
}
