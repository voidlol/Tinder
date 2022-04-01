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
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageText.setReplyMarkup(keyboardService.getSearchingKeyboard());
        ScrollingWrapper scroller = userDetailsCache.getScroller(userId);

        if ("LIKE".equals(queryData)) {
            Profile currentProfile = scroller.getCurrentProfile();
            profileClient.likeProfile(userId, currentProfile.getId());
            log.info("LIKE BTN: scroller size: {}, scroller current {}", scroller.getSize(), scroller.getCurrent());
            return getReply(userId, chatId, editMessageText, scroller, callbackQuery.getMessage().getMessageId());
        } else if ("NEXT".equals(queryData)) {
            log.info("NEXT BTN: scroller size: {}, scroller current {}", scroller.getSize(), scroller.getCurrent());
            return getReply(userId, chatId, editMessageText, scroller, callbackQuery.getMessage().getMessageId());
        } else {
            log.info("MENU BTN");
            deleteMessage(chatId, callbackQuery.getMessage().getMessageId(), restTemplate);
            return getInMenuMessage(userId, chatId);
        }
    }

    private BotApiMethod<?> getInMenuMessage(Long userId, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Меню");
        sendMessage.setReplyMarkup(keyboardService.getInMenuKeyboard());
        userDetailsCache.changeUserState(userId, BotState.IN_MENU);
        return sendMessage;
    }

    private BotApiMethod<?> getReply(Long userId, Long chatId, EditMessageText editMessageText, ScrollingWrapper scroller, Integer messageId) {
        if (scroller.isLast()) {
            scroller = new ScrollingWrapper(profileClient.getValidProfiles(userId));
            if (scroller.isEmpty()) {
                return getNoMoreValidListMessage(userId, chatId, messageId);
            }
            userDetailsCache.addScroller(userId, scroller);
            editMessageText.setText(scroller.getCurrentProfile().toString());
        } else {
            editMessageText.setText(scroller.getNextProfile().toString());
        }
        return editMessageText;
    }

    private SendMessage getNoMoreValidListMessage(Long userId, Long chatId, Integer messageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Не подходящих анкет :(");
        sendMessage.setReplyMarkup(keyboardService.getInMenuKeyboard());
        deleteMessage(chatId, messageId, restTemplate);
        userDetailsCache.changeUserState(userId, BotState.IN_MENU);
        return sendMessage;
    }


    @Override
    public BotState getBotState() {
        return BotState.SEARCHING;
    }
}
