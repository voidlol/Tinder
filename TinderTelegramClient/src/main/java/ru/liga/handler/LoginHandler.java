package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.cache.UserSessionCache;
import ru.liga.client.login.LoginClient;
import ru.liga.client.profile.ProfileClient;
import ru.liga.domain.Profile;
import ru.liga.domain.UserAuth;
import ru.liga.keyboards.KeyboardService;

@Component
@AllArgsConstructor
public class LoginHandler implements InputHandler {

    private final LoginClient loginClient;
    private final UserSessionCache userSessionCache;
    private final UserDetailsCache userDetailsCache;
    private final ProfileClient profileClient;
    private final KeyboardService keyboardService;

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processInputMessage(message);
    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }

    private BotApiMethod<?> processInputMessage(Message message) {
        Long userId = message.getFrom().getId();
        String password = message.getText();
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());

        String token = loginClient.login(new UserAuth(userId, password));
        if (!token.equals("Wrong password")) {
            userSessionCache.addTokenForUser(userId, token);
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            Profile userProfile = profileClient.getUserProfile(userId);
            reply.setText(userProfile.toString());
            reply.setReplyMarkup(keyboardService.getInMenuKeyboard());
        } else {
            reply.setText("Неверный пароль! Попробуйте еще раз");
        }
        return reply;
    }

    @Override
    public BotState getBotState() {
        return BotState.LOGIN_ASK_PASSWORD;
    }
}
