package ru.liga.botapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.botstate.BotState;
import ru.liga.client.cache.UserDetailsCache;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class TinderBot extends TelegramLongPollingBot {

    @Value("${botUserName}")
    private String botUsername;

    @Value("${botToken}")
    private String botToken;
    private final TelegramFacade telegramFacade;
    private final UserDetailsCache userDetailsCache;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        for (PartialBotApiMethod<?> method : telegramFacade.handleUpdate(update)) {
            addMessageToDelete(executeAnyMethod(method));
        }
    }

    private void addMessageToDelete(Message message) {
        if (message != null) {
            BotState currentBotState = userDetailsCache.getCurrentBotState(message.getChatId());
            switch (currentBotState) {
                case IN_MENU:
                case SEARCHING:
                case VIEWING:
                    userDetailsCache.setMessagesToDelete(message.getChatId(), Set.of(message.getMessageId()));
                    break;
                default:
                    userDetailsCache.addMessageToDelete(message.getChatId(), message.getMessageId());
            }
        }
    }

    private Message executeAnyMethod(PartialBotApiMethod<?> method) {
        try {
            if (method == null) return null;
            else if (method instanceof SendPhoto) return execute((SendPhoto) method);
            else if (method instanceof DeleteMessage) execute((DeleteMessage) method);
            else if (method instanceof AnswerCallbackQuery) execute((AnswerCallbackQuery) method);
            else if (method instanceof SendMessage) return execute((SendMessage) method);
        } catch (TelegramApiException e) {
            log.info(e.getMessage());
        }
        return null;
    }
}
