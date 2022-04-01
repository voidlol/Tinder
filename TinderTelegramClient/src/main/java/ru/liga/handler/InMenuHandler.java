package ru.liga.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        String text = message.getText();

        SendMessage reply = new SendMessage();
        reply.setChatId(chatId.toString());

        switch (text) {
            case "Поиск":
                List<Profile> validProfiles = profileClient.getValidProfiles(userId);
                if (validProfiles.isEmpty()) {
                    reply.setText("Нет подходящих анкет");
                    reply.setReplyMarkup(keyboardService.getInMenuKeyboard());
                    return reply;
                }
                ScrollingWrapper searchScroller = new ScrollingWrapper(validProfiles);
                userDetailsCache.addScroller(userId, searchScroller);
                reply.setText(searchScroller.getCurrentProfile().toString());
                reply.setReplyMarkup(keyboardService.getSearchingKeyboard());
                userDetailsCache.changeUserState(userId, BotState.SEARCHING);
                return reply;
            case "Избранные":
                Set<Profile> favorites = profileClient.getFavorites(userId);
                if (favorites.isEmpty()) {
                    reply.setText("Нет избранных анкет");
                    reply.setReplyMarkup(keyboardService.getInMenuKeyboard());
                    return reply;
                }
                ScrollingWrapper favoritesScroller = new ScrollingWrapper(favorites);
                userDetailsCache.addScroller(userId, favoritesScroller);
                reply.setText(favoritesScroller.getCurrentProfile().toString());
                reply.setReplyMarkup(keyboardService.getFavoritesKeyboard());
                userDetailsCache.changeUserState(userId, BotState.VIEWING);
                return reply;
            default:
                reply.setText("Выберите пункт меню!");
                reply.setReplyMarkup(keyboardService.getInMenuKeyboard());
                return reply;
        }
        //Поиск //Избранные //Профиль

    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }

    @Override
    public BotState getBotState() {
        return BotState.IN_MENU;
    }
}
