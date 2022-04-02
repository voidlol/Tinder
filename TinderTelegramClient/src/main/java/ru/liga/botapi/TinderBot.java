package ru.liga.botapi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TinderBot extends TelegramLongPollingBot {

    @Value("${bot.userName}")
    private String botUsername;

    @Value("${bot.botToken}")
    private String botToken;
    private final TelegramFacade telegramFacade;

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
            executeAnyMethod(method);
        }
    }

    public void executeAnyMethod(PartialBotApiMethod<?> method) {
        try {
            if (method == null) return;
            else if (method instanceof SendPhoto) execute((SendPhoto) method);
            else if (method instanceof DeleteMessage) execute((DeleteMessage) method);
            else if (method instanceof AnswerCallbackQuery) execute((AnswerCallbackQuery) method);
            else if (method instanceof SendMessage) execute((SendMessage) method);
            else if (method instanceof EditMessageText) execute((EditMessageText) method);
            else if (method instanceof EditMessageMedia) execute((EditMessageMedia) method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
