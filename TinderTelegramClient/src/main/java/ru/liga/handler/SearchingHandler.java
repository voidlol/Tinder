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
import ru.liga.client.profile.ProfileClient;
import ru.liga.domain.Profile;
import ru.liga.domain.ScrollingWrapper;
import ru.liga.service.BotMethodService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchingHandler implements InputHandler {

    private final ProfileClient profileClient;
    private final UserDetailsCache userDetailsCache;
    private final ImageClient imageClient;
    private final BotMethodService botMethodService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String queryData = callbackQuery.getData();
        log.info("User: {}, State: {}, Button: {}", userId, userDetailsCache.getCurrentBotState(userId), queryData);
        ScrollingWrapper scroller = userDetailsCache.getScroller(userId);
        List<PartialBotApiMethod<?>> methodsToExecute = new ArrayList<>();

        if ("LIKE".equals(queryData)) {
            Profile currentProfile = scroller.getCurrentProfile();
            boolean isReciprocity = profileClient.likeProfile(userId, currentProfile.getId());
            if (isReciprocity) {
                methodsToExecute.add(botMethodService.getPopUpMethod(callbackQuery, "Вы любимы"));
            }
            methodsToExecute.addAll(getReply(userId, scroller, callbackQuery));
            return methodsToExecute;
        } else if ("NEXT".equals(queryData)) {
            return getReply(userId, scroller, callbackQuery);
        } else {
            methodsToExecute.add(botMethodService.getDeleteMethod(chatId, callbackQuery.getMessage().getMessageId()));
            methodsToExecute.add(botMethodService.getMenuMethod(chatId));
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            return methodsToExecute;

        }
    }

    private List<PartialBotApiMethod<?>> getReply(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.getSize() == 1) {
            return Collections.emptyList();
        }
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();
        if (scroller.isLast()) {
            scroller = new ScrollingWrapper(profileClient.getValidProfiles(userId));
            if (scroller.isEmpty()) {
                methods.add(botMethodService.getPopUpMethod(callbackQuery, "Не остлоась подходящих анкет"));
                methods.add(botMethodService.getDeleteMethod(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId()));
                methods.add(botMethodService.getMenuMethod(callbackQuery.getMessage().getChatId()));
                userDetailsCache.changeUserState(userId, BotState.IN_MENU);
                return methods;
            }
            userDetailsCache.addScroller(userId, scroller);
            Profile currentProfile = scroller.getCurrentProfile();
            File imageForProfile = imageClient.getImageForProfile(currentProfile);
            methods.add(botMethodService.getEditMessageMediaMethod(imageForProfile, BotState.SEARCHING, callbackQuery));
        } else {
            Profile currentProfile = scroller.getNextProfile();
            File imageForProfile = imageClient.getImageForProfile(currentProfile);
            methods.add(botMethodService.getEditMessageMediaMethod(imageForProfile, BotState.SEARCHING, callbackQuery));
        }
        return methods;
    }


    @Override
    public BotState getBotState() {
        return BotState.SEARCHING;
    }

}
