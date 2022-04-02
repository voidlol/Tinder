package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.cache.UserSessionCache;
import ru.liga.client.login.LoginClient;
import ru.liga.domain.UserAuth;
import ru.liga.service.BotMethodService;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LoginHandler implements InputHandler {

    private final LoginClient loginClient;
    private final UserSessionCache userSessionCache;
    private final UserDetailsCache userDetailsCache;
    private final BotMethodService botMethodService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return processInputMessage(message);
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }

    private List<PartialBotApiMethod<?>> processInputMessage(Message message) {
        Long userId = message.getFrom().getId();
        String password = message.getText();
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();

        String token = loginClient.login(new UserAuth(userId, password));
        if (!token.equals("Wrong password")) {
            userSessionCache.addTokenForUser(userId, token);
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            methods.add(botMethodService.getMenuMethod(message.getChatId()));
        } else {
            methods.add(botMethodService.getSendMessage(message.getChatId(), "Неверный пароль! Попробуйте еще раз"));
        }
        return methods;
    }

    @Override
    public BotState getBotState() {
        return BotState.LOGIN_ASK_PASSWORD;
    }
}
