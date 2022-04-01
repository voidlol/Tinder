package ru.liga.handler;

import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.profile.ProfileClient;
import ru.liga.domain.Profile;
import ru.liga.domain.ScrollingWrapper;
import ru.liga.keyboards.KeyboardService;

import java.util.List;
import java.util.Set;

@Component
public class InMenuHandler implements InputHandler {

    private final ProfileClient profileClient;
    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;

    public InMenuHandler(ProfileClient profileClient, UserDetailsCache userDetailsCache, KeyboardService keyboardService) {
        this.profileClient = profileClient;
        this.userDetailsCache = userDetailsCache;
        this.keyboardService = keyboardService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return null;
    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        EditMessageText reply = new EditMessageText();
        reply.setChatId(chatId.toString());
        reply.setMessageId(callbackQuery.getMessage().getMessageId());

        switch (data) {
            case "SEARCH":
                List<Profile> validProfiles = profileClient.getValidProfiles(userId);
                if (validProfiles.isEmpty()) {
                    return sendCallbackQuery("Не осталось анкет :(", callbackQuery);
                }
                ScrollingWrapper searchScroller = new ScrollingWrapper(validProfiles);
                userDetailsCache.addScroller(userId, searchScroller);
                reply.setText(searchScroller.getCurrentProfile().toString());
                reply.setReplyMarkup(keyboardService.getSearchingKeyboard());
                userDetailsCache.changeUserState(userId, BotState.SEARCHING);
                return reply;
            case "FAVORITES":
                Set<Profile> favorites = profileClient.getFavorites(userId);
                if (favorites.isEmpty()) {
                    return sendCallbackQuery("Не избранных анкет :(", callbackQuery);
                }
                ScrollingWrapper favoritesScroller = new ScrollingWrapper(favorites);
                userDetailsCache.addScroller(userId, favoritesScroller);
                reply.setText(favoritesScroller.getCurrentProfile().toString());
                reply.setReplyMarkup(keyboardService.getFavoritesKeyboard());
                userDetailsCache.changeUserState(userId, BotState.VIEWING);
                return reply;
            default:
                reply.setText(profileClient.getUserProfile(userId).toString());
                reply.setReplyMarkup(keyboardService.getInMenuKeyboard2());
                return reply;
        }
    }

    @Override
    public BotState getBotState() {
        return BotState.IN_MENU;
    }

}
