package ru.liga.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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

@Component
@AllArgsConstructor
@Slf4j
public class SearchingHandler implements InputHandler {

    private final ProfileClient profileClient;
    private final UserDetailsCache userDetailsCache;
    private final KeyboardService keyboardService;
    private final RestTemplate restTemplate;

    @Override
    public BotApiMethod<?> handle(Message message) {
        return null;
    }

    @Override
    public BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String queryData = callbackQuery.getData();
        log.info("User: {}, State: {}, Button: {}", userId, userDetailsCache.getCurrentBotState(userId), queryData);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setReplyMarkup(keyboardService.getSearchingKeyboard());
        ScrollingWrapper scroller = userDetailsCache.getScroller(userId);

        if ("LIKE".equals(queryData)) {
            Profile currentProfile = scroller.getCurrentProfile();
            profileClient.likeProfile(userId, currentProfile.getId());
            return getReply(userId, editMessageText, scroller, callbackQuery);
        } else if ("NEXT".equals(queryData)) {
            return getReply(userId, editMessageText, scroller, callbackQuery);
        } else {
            editMessageText.setReplyMarkup(keyboardService.getInMenuKeyboard2());
            editMessageText.setText("Меню");
            userDetailsCache.changeUserState(userId, BotState.IN_MENU);
            return editMessageText;
        }
    }

    private BotApiMethod<?> getReply(Long userId, EditMessageText editMessageText, ScrollingWrapper scroller, CallbackQuery callbackQuery) {
        if (scroller.isLast()) {
            scroller = new ScrollingWrapper(profileClient.getValidProfiles(userId));
            if (scroller.isEmpty()) {
                editMessageText.setText("Меню");
                editMessageText.setReplyMarkup(keyboardService.getInMenuKeyboard2());
                changeMessage(restTemplate, editMessageText);
                userDetailsCache.changeUserState(userId, BotState.IN_MENU);
                return sendCallbackQuery("Нет подходящих анкет", callbackQuery);
            }
            userDetailsCache.addScroller(userId, scroller);
            editMessageText.setText(scroller.getCurrentProfile().toString());
        } else {
            editMessageText.setText(scroller.getNextProfile().toString());
        }
        return editMessageText;
    }


    @Override
    public BotState getBotState() {
        return BotState.SEARCHING;
    }
}
