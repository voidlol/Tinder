package ru.liga.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.profile.ImageClient;
import ru.liga.domain.Profile;
import ru.liga.domain.ScrollingWrapper;
import ru.liga.service.BotMethodService;
import ru.liga.service.ProfileService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavoritesHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final ProfileService profileService;
    private final BotMethodService botMethodService;
    private final ImageClient imageClient;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return Collections.emptyList();
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String queryData = callbackQuery.getData();
        log.info("User: {}, State: {}, Button: {}", userId, userDetailsCache.getCurrentBotState(userId), queryData);
        ScrollingWrapper scroller = userDetailsCache.getScroller(userId);

        if ("DISLIKE".equals(queryData)) {
            Profile currentProfile = scroller.getCurrentProfile();
            profileService.unlikeProfile(userId, currentProfile.getId());
            return dislikeMethods(userId, scroller, callbackQuery);
        } else if ("NEXT".equals(queryData)) {
            return nextMethods(userId, scroller, callbackQuery);
        } else if ("PREV".equals(queryData)) {
            return getPrevMethod(userId, scroller, callbackQuery);
        } else {
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            return List.of(botMethodService.getDeleteMethod(chatId, callbackQuery.getMessage().getMessageId()),
                    botMethodService.getMenuMethod(chatId));
        }
    }

    private List<PartialBotApiMethod<?>> getPrevMethod(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        Profile profile;
        File profileImage;
        if (scroller.isFirst()) {
            scroller = new ScrollingWrapper(profileService.getFavorites(userId));
            userDetailsCache.addScroller(userId, scroller);
            if (scroller.isEmpty()) {
                return returnToMenu(userId, callbackQuery);
            }
            profile = scroller.getLastProfile();
        } else {
            profile = scroller.getPrevProfile();
        }
        profileImage = imageClient.getImageForProfile(profile);
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        return List.of(botMethodService.getDeleteMethod(chatId, messageId),
                botMethodService.getSendPhotoMethod(profileImage, chatId, BotState.VIEWING, profile.getName()));
    }

    private List<PartialBotApiMethod<?>> dislikeMethods(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.getSize() == 1 && scroller.isLast()) {
            return returnToMenu(userId, callbackQuery);
        } else {
            return getNextMethod(userId, scroller, callbackQuery);
        }
    }

    private List<PartialBotApiMethod<?>> nextMethods(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.getSize() == 1) {
            return Collections.singletonList(botMethodService.getPopUpMethod(callbackQuery, "Это единственная анкета!"));
        }
        return getNextMethod(userId, scroller, callbackQuery);
    }

    private List<PartialBotApiMethod<?>> getNextMethod(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        File profileImage;
        Profile profile;
        if (scroller.isLast()) {
            scroller = new ScrollingWrapper(profileService.getFavorites(userId));
            userDetailsCache.addScroller(userId, scroller);
            if (scroller.isEmpty()) {
                return returnToMenu(userId, callbackQuery);
            }
            profile = scroller.getCurrentProfile();
        } else {
            profile = scroller.getNextProfile();
        }
        profileImage = imageClient.getImageForProfile(profile);
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        return List.of(botMethodService.getDeleteMethod(chatId, messageId),
                botMethodService.getSendPhotoMethod(profileImage, chatId, BotState.VIEWING, profile.getName()));
    }

    private List<PartialBotApiMethod<?>> returnToMenu(Long userId, CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();
        methods.add(botMethodService.getPopUpMethod(callbackQuery, "Не осталось избранных :("));
        userDetailsCache.changeUserState(userId, BotState.IN_MENU);
        methods.add(botMethodService.getDeleteMethod(chatId, messageId));
        methods.add(botMethodService.getMenuMethod(chatId));
        return methods;
    }


    @Override
    public BotState getBotState() {
        return BotState.VIEWING;
    }
}
