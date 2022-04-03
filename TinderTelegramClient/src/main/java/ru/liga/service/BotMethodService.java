package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.liga.botstate.BotState;

import java.io.File;

@Service
@RequiredArgsConstructor
public class BotMethodService {

    private final KeyboardService keyboardService;
    private final TextMessageService textMessageService;

    public DeleteMessage getDeleteMethod(Long chatId, Integer messageId) {
        return new DeleteMessage(chatId.toString(), messageId);
    }

    public SendPhoto getSendPhotoMethod(File photo, Long chatId, BotState targetState, String caption) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photo));
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setReplyMarkup(getKeyboard(targetState));
        sendPhoto.setCaption(caption);
        return sendPhoto;
    }

    public AnswerCallbackQuery getPopUpMethod(CallbackQuery callbackQuery, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public SendMessage getMenuMethod(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(getKeyboard(BotState.IN_MENU));
        sendMessage.setText(textMessageService.getText("text.menu"));
        return sendMessage;
    }

    public SendMessage getSendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId.toString());
        return sendMessage;
    }

    public SendMessage getSendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = getSendMessage(chatId, text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public SendMessage getSendMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = getSendMessage(chatId, text);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    private InlineKeyboardMarkup getKeyboard(BotState state) {
        switch (state) {
            case SEARCHING:
                return keyboardService.getSearchingKeyboard();
            case VIEWING:
                return keyboardService.getFavoritesKeyboard();
            default:
                return keyboardService.getInMenuKeyboard();
        }
    }

}
