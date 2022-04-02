package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.keyboards.ButtonNameEnum;
import ru.liga.keyboards.KeyboardService;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class WelcomeHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;
    private final RestTemplate restTemplate;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public BotState getBotState() {
        return BotState.WELCOME;
    }

    private List<PartialBotApiMethod<?>> processMessage(Message message) {
        String text = message.getText();
        Long userId = message.getFrom().getId();
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());

        if (text.equals(ButtonNameEnum.LOGIN_BUTTON.getButtonName())) {
            userDetailsCache.changeUserState(userId, BotState.LOGIN_ASK_PASSWORD);
            reply.setText(BotState.REGISTER_ASK_PASSWORD.getMessage());
        } else if (text.equals(ButtonNameEnum.REGISTRATION_BUTTON.getButtonName())) {
            if (userDetailsCache.isRegistered(userId)) {
                reply.setText(BotState.REGISTERED.getMessage());
                reply.setReplyMarkup(keyboardService.getWelcomeKeyboard());
            } else {
                reply.setText(BotState.REGISTER_ASK_PASSWORD.getMessage());
                userDetailsCache.changeUserState(userId, BotState.REGISTER_ASK_PASSWORD);
            }
        }
        return Collections.singletonList(reply);
    }
}
