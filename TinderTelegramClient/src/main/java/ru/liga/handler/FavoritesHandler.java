package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.profile.ProfileClient;
import ru.liga.domain.Profile;

@Component
@AllArgsConstructor
public class FavoritesHandler implements InputHandler {

    private final UserDetailsCache userDetailsCache;
    private final ProfileClient profileClient;

    @Override
    public BotApiMethod<?> handle(Message message) {
        return null;
    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String queryData = callbackQuery.getData();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());

        switch (queryData) {
            case "DISLIKE":
                Profile currentProfile = userDetailsCache.getScroller(userId).getCurrentProfile();
                profileClient.unlikeProfile(userId, currentProfile.getId());
                editMessageText.setText(userDetailsCache.getScroller(userId).getNextProfile().toString());
                return editMessageText;
            case "NEXT":
                editMessageText.setText(userDetailsCache.getScroller(userId).getNextProfile().toString());
                return editMessageText;
            case "MENU":
                return null;
        }


        return null;
    }

    @Override
    public BotState getBotState() {
        return BotState.VIEWING;
    }
}
