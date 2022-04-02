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
import ru.liga.keyboards.KeyboardService;
import ru.liga.service.BotMethodService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavoritesHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final ProfileClient profileClient;
    private final KeyboardService keyboardService;
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
            profileClient.unlikeProfile(userId, currentProfile.getId());
            return getReply(userId, scroller, callbackQuery);
        } else if ("NEXT".equals(queryData)) {
            return getReply(userId, scroller, callbackQuery);
        } else {
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            return List.of(botMethodService.getDeleteMethod(chatId, callbackQuery.getMessage().getMessageId()),
                    botMethodService.getMenuMethod(chatId));
        }
    }

    private List<PartialBotApiMethod<?>> getReply(Long userId, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.getSize() == 1) {
            return Collections.emptyList();
        }
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();
        if (scroller.isLast()) {
            scroller = new ScrollingWrapper(profileClient.getFavorites(userId));
            userDetailsCache.addScroller(userId, scroller);
            if (scroller.isEmpty()) {
                methods.add(botMethodService.getPopUpMethod(callbackQuery, "Не осталось избранных :("));
                methods.add(botMethodService.getDeleteMethod(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId()));
                methods.add(botMethodService.getMenuMethod(callbackQuery.getMessage().getChatId()));
                userDetailsCache.changeUserState(userId, BotState.IN_MENU);
                return methods;
            }
            Profile currentProfile = scroller.getCurrentProfile();
            File imageForProfile = imageClient.getImageForProfile(currentProfile);
            methods.add(botMethodService.getEditMessageMediaMethod(imageForProfile, BotState.VIEWING, callbackQuery));
        } else {
            Profile nextProfile = scroller.getNextProfile();
            File imageForProfile = imageClient.getImageForProfile(nextProfile);
            methods.add(botMethodService.getEditMessageMediaMethod(imageForProfile, BotState.VIEWING, callbackQuery));
        }
        return methods;
    }


    @Override
    public BotState getBotState() {
        return BotState.VIEWING;
    }
}
