package ru.liga.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botapi.TinderBot;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.client.profile.ProfileClient;
import ru.liga.domain.Profile;

@Component
@AllArgsConstructor
public class SearchingHandler implements InputHandler {

    private final ProfileClient profileClient;
    private final UserDetailsCache userDetailsCache;

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
            case "LIKE":
                Profile currentProfile = userDetailsCache.getScroller(userId).getCurrentProfile();
                profileClient.likeProfile(userId, currentProfile.getId());
                editMessageText.setText(userDetailsCache.getScroller(userId).getNextProfile().toString());
                return editMessageText;
            case "NEXT":
                editMessageText.setText(userDetailsCache.getScroller(userId).getNextProfile().toString());
                return editMessageText;
            case "MENU":
                break;
        }


        return null;
    }

    @Override
    public BotState getBotState() {
        return BotState.SEARCHING;
    }
}
