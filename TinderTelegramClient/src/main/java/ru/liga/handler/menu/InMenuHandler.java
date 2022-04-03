package ru.liga.handler.menu;

import lombok.RequiredArgsConstructor;
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
import ru.liga.handler.InputHandler;
import ru.liga.service.BotMethodService;
import ru.liga.service.ProfileService;
import ru.liga.service.TextMessageService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InMenuHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final ImageClient imageClient;
    private final BotMethodService botMethodService;
    private final ProfileService profileService;
    private final TextMessageService textMessageService;

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return List.of(botMethodService.getDeleteMethod(message.getChatId(), message.getMessageId()));
    }

    @Override
    public List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        switch (data) {
            case QueryData.SEARCH:
                return getReply(profileService.getValidProfiles(userId), BotState.SEARCHING, callbackQuery);
            case QueryData.FAVORITES:
                return getReply(profileService.getFavorites(userId), BotState.VIEWING, callbackQuery);
            default:
                Profile userProfile = profileService.getUserProfile(userId);
                File imageForProfile = imageClient.getImageForProfile(userProfile);
                return List.of(botMethodService.getDeleteMethod(chatId, callbackQuery.getMessage().getMessageId()),
                        botMethodService.getSendPhotoMethod(imageForProfile, chatId, BotState.IN_MENU, userProfile.getCaption()));
        }
    }

    private List<PartialBotApiMethod<?>> getReply(List<Profile> list, BotState targetState, CallbackQuery callbackQuery) {
        List<PartialBotApiMethod<?>> methods = new ArrayList<>();
        String popUpText;
        if (targetState == BotState.SEARCHING) {
            popUpText = textMessageService.getText("text.no.search");
        } else {
            popUpText = textMessageService.getText("text.no.favorites");
        }
        if (list.isEmpty()) {
            return Collections.singletonList(botMethodService.getPopUpMethod(callbackQuery, popUpText));
        }
        methods.add(botMethodService.getDeleteMethod(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId()));
        ScrollingWrapper searchScroller = new ScrollingWrapper(list);
        userDetailsCache.addScroller(callbackQuery.getFrom().getId(), searchScroller);
        Profile currentProfile = searchScroller.getCurrentProfile();
        File imageForProfile = imageClient.getImageForProfile(currentProfile);
        String caption;
        caption = currentProfile.getCaption();
        if (targetState == BotState.VIEWING) {
            String relation = profileService.getRelation(callbackQuery.getFrom().getId(), currentProfile.getId());
            caption = caption + ", " + relation;
        }
        methods.add(botMethodService.getSendPhotoMethod(imageForProfile, callbackQuery.getMessage().getChatId(), targetState, caption));
        userDetailsCache.changeUserState(callbackQuery.getFrom().getId(), targetState);
        return methods;
    }

    @Override
    public BotState getBotState() {
        return BotState.IN_MENU;
    }

}
