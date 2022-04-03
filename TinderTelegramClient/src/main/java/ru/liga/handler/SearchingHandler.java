package ru.liga.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.profile.ImageClient;
import ru.liga.config.QueryData;
import ru.liga.domain.Profile;
import ru.liga.domain.ScrollingWrapper;
import ru.liga.service.BotMethodService;
import ru.liga.service.ProfileService;
import ru.liga.service.TextMessageService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchingHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final ImageClient imageClient;
    private final ProfileService profileService;
    private final BotMethodService botMethodService;
    private final TextMessageService textMessageService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return List.of(botMethodService.getDeleteMethod(message.getChatId(), message.getMessageId()));
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String queryData = callbackQuery.getData();
        log.info("User: {}, State: {}, Button: {}", userId, userDetailsCache.getCurrentBotState(userId), queryData);
        ScrollingWrapper scroller = userDetailsCache.getScroller(userId);
        List<PartialBotApiMethod<?>> methodsToExecute = new ArrayList<>();

        if (QueryData.LIKE.equals(queryData)) {
            Profile currentProfile = scroller.getCurrentProfile();
            boolean isReciprocity = profileService.likeProfile(userId, currentProfile.getId());
            if (isReciprocity) {
                methodsToExecute.add(botMethodService.getPopUpMethod(callbackQuery, textMessageService.getText("text.reciprocity")));
            }
            methodsToExecute.addAll(likeMethods(userId, scroller, callbackQuery));
            return methodsToExecute;
        } else if (QueryData.NEXT.equals(queryData)) {
            return nextMethods(userId, scroller, callbackQuery);
        } else {
            methodsToExecute.add(botMethodService.getDeleteMethod(chatId, callbackQuery.getMessage().getMessageId()));
            methodsToExecute.add(botMethodService.getMenuMethod(chatId));
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            return methodsToExecute;

        }
    }

    private List<PartialBotApiMethod<?>> likeMethods(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.getSize() == 1 && scroller.isLast()) {
            return returnToMenu(userId, callbackQuery);
        } else {
            return getNextMethod(userId, scroller, callbackQuery);
        }
    }

    private List<PartialBotApiMethod<?>> nextMethods(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.getSize() == 1) {
            return Collections.singletonList(botMethodService.getPopUpMethod(callbackQuery, textMessageService.getText("text.only.profile")));
        }
        return getNextMethod(userId, scroller, callbackQuery);
    }

    private List<PartialBotApiMethod<?>> getNextMethod(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        Profile profile;
        File profileImage;
        if (scroller.isLast()) {
            scroller = new ScrollingWrapper(profileService.getValidProfiles(userId));
            if (scroller.isEmpty()) {
                return returnToMenu(userId, callbackQuery);
            }
            userDetailsCache.addScroller(userId, scroller);
            profile = scroller.getCurrentProfile();
        } else {
            profile = scroller.getNextProfile();
        }
        profileImage = imageClient.getImageForProfile(profile);
        return List.of(botMethodService.getDeleteMethod(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId()),
                botMethodService.getSendPhotoMethod(profileImage, callbackQuery.getMessage().getChatId(), BotState.SEARCHING, profile.getCaption()));
    }

    private List<PartialBotApiMethod<?>> returnToMenu(Long userId, CallbackQuery callbackQuery) {
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();
        methods.add(botMethodService.getPopUpMethod(callbackQuery, textMessageService.getText("text.no.search")));
        methods.add(botMethodService.getDeleteMethod(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId()));
        methods.add(botMethodService.getMenuMethod(callbackQuery.getMessage().getChatId()));
        userDetailsCache.changeUserState(userId, BotState.IN_MENU);
        return methods;
    }


    @Override
    public BotState getBotState() {
        return BotState.SEARCHING;
    }


    @Getter
    @RequiredArgsConstructor
    static class MessageData {
        private final File profileImage;
        private final Profile profile;
    }
}
