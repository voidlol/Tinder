package ru.liga.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.cache.UserDetailsCache;
import ru.liga.cache.UserSessionCache;
import ru.liga.client.login.LoginClient;
import ru.liga.client.profile.ProfileClient;
import ru.liga.domain.Profile;
import ru.liga.domain.UserAuth;

@Component
public class LoginHandler implements InputHandler {

    private final LoginClient loginClient;
    private final UserSessionCache userSessionCache;
    private final UserDetailsCache userDetailsCache;
    private final ProfileClient profileClient;

    public LoginHandler(LoginClient loginClient, UserSessionCache userSessionCache, UserDetailsCache userDetailsCache, ProfileClient profileClient) {
        this.loginClient = loginClient;
        this.userSessionCache = userSessionCache;
        this.userDetailsCache = userDetailsCache;
        this.profileClient = profileClient;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processInputMessage(message);
    }

    private BotApiMethod<?> processInputMessage(Message message) {
        Long userId = message.getFrom().getId();
        String password = message.getText();
        SendMessage reply = new SendMessage();

        String token = loginClient.login(new UserAuth(userId, password));
        if (token.length() > 20) {
            userSessionCache.addTokenForUser(userId, token);
            userDetailsCache.changeUserState(userId, BotState.MY_PROFILE);
            Profile userProfile = profileClient.getUserProfile(userId);
            reply.setText(userProfile.toString());
        } else {
            reply.setText("Wrong password! Try again:");
        }
        return reply;
    }

    @Override
    public BotState getBotState() {
        return BotState.LOGIN_ASK_PASSWORD;
    }
}
