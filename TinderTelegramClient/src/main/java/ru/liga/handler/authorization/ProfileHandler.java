package ru.liga.handler.authorization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.cache.UserSessionCache;
import ru.liga.client.login.LoginClient;
import ru.liga.client.profile.ImageClient;
import ru.liga.client.profile.TranslatorClient;
import ru.liga.client.registration.RegistrationClient;
import ru.liga.domain.Profile;
import ru.liga.domain.SexType;
import ru.liga.domain.User;
import ru.liga.domain.UserAuth;
import ru.liga.handler.InputHandler;
import ru.liga.service.BotMethodService;
import ru.liga.service.KeyboardService;
import ru.liga.service.TextMessageService;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProfileHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final RegistrationClient registrationClient;
    private final LoginClient loginClient;
    private final KeyboardService keyboardService;
    private final UserSessionCache userSessionCache;
    private final BotMethodService botMethodService;
    private final TextMessageService textMessageService;
    private final ImageClient imageClient;
    private final TranslatorClient translatorClient;

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

        if (currentBotState == BotState.PROFILE_FILLING_ASK_GENDER) {
            SexType userSex = SexType.valueOf(callbackQuery.getData());
            profile.setSexType(userSex);
            userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_DESCRIPTION);
            return Collections.singletonList(botMethodService.getSendMessage(
                    callbackQuery.getMessage().getChatId(),
                    textMessageService.getText("reply.askDescription")));
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
            Profile translatedProfile = translatorClient.getTranslatedProfile(profile);
            File imageForProfile = imageClient.getImageForProfile(translatedProfile);
            List<PartialBotApiMethod<?>> methods = userDetailsCache.getMessagesToDelete(userId).stream()
                    .map(i -> botMethodService.getDeleteMethod(callbackQuery.getMessage().getChatId(), i))
                    .collect(Collectors.toList());
            methods.add(botMethodService.getSendPhotoMethod(imageForProfile,
                    callbackQuery.getMessage().getChatId(),
                    BotState.IN_MENU,
                    translatedProfile.getCaption()));
            return methods;
        }
        return Collections.emptyList();
    }

    private List<PartialBotApiMethod<?>> processMessage(Message message) {
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        BotState currentBotState = userDetailsCache.getCurrentBotState(userId);
        User user = userDetailsCache.getUser(userId);
        Profile profile = user.getProfile();

        if (currentBotState == BotState.PROFILE_FILLING_ASK_NAME) {
            userDetailsCache.addMessageToDelete(userId, message.getMessageId());
            profile.setName(message.getText());
            userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_GENDER);
            return Collections.singletonList(botMethodService.getSendMessage(
                    chatId,
                    textMessageService.getText("reply.askGender"),
                    keyboardService.getMySexKeyboard()));
        } else if (currentBotState == BotState.PROFILE_FILLING_ASK_DESCRIPTION) {
            userDetailsCache.addMessageToDelete(userId, message.getMessageId());
            profile.setDescription(message.getText());
            userDetailsCache.changeUserState(userId, BotState.PROFILE_FILLING_ASK_LOOKING_FOR);
            return Collections.singletonList(botMethodService.getSendMessage(
                    chatId,
                    textMessageService.getText("reply.askLookingFor"),
                    keyboardService.getLookingForKeyboard()));
        }
        return Collections.singletonList(botMethodService.getDeleteMethod(chatId, message.getMessageId()));
    }
}
