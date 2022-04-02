package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.cache.UserSessionCache;
import ru.liga.client.login.LoginClient;
import ru.liga.client.registration.RegistrationClient;
import ru.liga.domain.Profile;
import ru.liga.domain.SexType;
import ru.liga.domain.User;
import ru.liga.domain.UserAuth;
import ru.liga.service.BotMethodService;
import ru.liga.service.KeyboardService;
import ru.liga.service.ProfileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class ProfileHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final RegistrationClient registrationClient;
    private final LoginClient loginClient;
    private final KeyboardService keyboardService;
    private final UserSessionCache userSessionCache;
    private final BotMethodService botMethodService;

    @Override
    public BotState getBotState() {
        return BotState.PROFILE_FILLING;
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return processMessage(message);
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        BotState currentBotState = userDetailsCache.getCurrentBotState(userId);
        User user = userDetailsCache.getUser(userId);
        Profile profile = user.getProfile();
        SendMessage reply = new SendMessage();
        reply.setChatId(callbackQuery.getMessage().getChatId().toString());
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();

        if (currentBotState == BotState.PROFILE_FILLING_ASK_GENDER) {
            SexType userSex = SexType.valueOf(callbackQuery.getData());
            profile.setSexType(userSex);
            reply.setText(BotState.PROFILE_FILLING_ASK_DESCRIPTION.getMessage());
            userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_DESCRIPTION);
        } else if (currentBotState == BotState.PROFILE_FILLING_ASK_LOOKING_FOR) {
            Set<SexType> lookingFor;
            try {
                lookingFor = Set.of(SexType.valueOf(callbackQuery.getData()));
            } catch (IllegalArgumentException e) {
                lookingFor = Set.of(SexType.MALE, SexType.FEMALE);
            }
            profile.setLookingFor(lookingFor);
            registrationClient.registerUser(user);
            String token = loginClient.login(new UserAuth(userId, user.getPassword()));
            userSessionCache.addTokenForUser(userId, token);
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            reply = botMethodService.getMenuMethod(callbackQuery.getMessage().getChatId());
        }
        methods.add(reply);
        return methods;
    }

    private List<PartialBotApiMethod<?>> processMessage(Message message) {
        Long userId = message.getFrom().getId();
        BotState currentBotState = userDetailsCache.getCurrentBotState(userId);
        User user = userDetailsCache.getUser(userId);
        Profile profile = user.getProfile();
        String text = message.getText();
        SendMessage reply = new SendMessage();
        reply.setChatId(message.getChatId().toString());
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();

        switch (currentBotState) {
            case PROFILE_FILLING_ASK_NAME:
                profile.setName(text);
                reply.setText(BotState.PROFILE_FILLING_ASK_GENDER.getMessage());
                reply.setReplyMarkup(keyboardService.getMySexKeyboard());
                userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_GENDER);
                break;
            case PROFILE_FILLING_ASK_DESCRIPTION:
                profile.setDescription(text);
                reply.setText(BotState.PROFILE_FILLING_ASK_LOOKING_FOR.getMessage());
                reply.setReplyMarkup(keyboardService.getLookingForKeyboard());
                userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_LOOKING_FOR);
                break;
        }
        methods.add(reply);
        return methods;
    }
}
