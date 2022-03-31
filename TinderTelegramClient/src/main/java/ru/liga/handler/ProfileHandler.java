package ru.liga.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.domain.Profile;
import ru.liga.domain.SexType;
import ru.liga.domain.User;
import ru.liga.keyboards.KeyboardService;

import java.util.HashSet;

@Component
public class ProfileHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;

    @Autowired
    public ProfileHandler(UserDetailsCache userDetailsCache, KeyboardService keyboardService) {
        this.userDetailsCache = userDetailsCache;
        this.keyboardService = keyboardService;
    }

    @Override
    public BotState getBotState() {
        return BotState.LOGIN;
    }

    @Override
    public SendMessage handle(Message message) {
        return processMessage(message);
    }

    private SendMessage processMessage(Message message) {
        Long userId = message.getFrom().getId();
        BotState currentBotState = userDetailsCache.getCurrentBotState(userId);
        User user = userDetailsCache.getUser(userId);
        Profile profile = user.getProfile();
        String text = message.getText();
        SendMessage reply = new SendMessage();

        switch (currentBotState) {
            case PROFILE_FILLING_ASK_NAME:
                profile.setName(text);
                reply.setText(BotState.PROFILE_FILLING_ASK_GENDER.getMessage());
                reply.setReplyMarkup(keyboardService.getMySexKeyboard());
                userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_GENDER);

            case PROFILE_FILLING_ASK_DESCRIPTION:
                profile.setDescription(text);
                reply.setText(BotState.PROFILE_FILLING_ASK_LOOKING_FOR.getMessage());
                reply.setReplyMarkup(keyboardService.getLookingForKeyboard());
                userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_LOOKING_FOR);



        }


    }
}
