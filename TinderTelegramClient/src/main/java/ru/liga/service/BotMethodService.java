package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.liga.botstate.BotState;

import java.io.File;

@Service
@RequiredArgsConstructor
public class BotMethodService {

    private final KeyboardService keyboardService;

    public DeleteMessage getDeleteMethod(Long chatId, Integer messageId) {
        return new DeleteMessage(chatId.toString(), messageId);
    }

    public SendPhoto getSendPhotoMethod(File photo, Long chatId, BotState targetState) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photo));
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setReplyMarkup(getKeyboard(targetState));
        return sendPhoto;
    }

    public AnswerCallbackQuery getPopUpMethod(CallbackQuery callbackQuery, String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

    public EditMessageMedia getEditMessageMediaMethod(File newPhoto, BotState nextState, CallbackQuery callbackQuery) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageMedia.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageMedia.setReplyMarkup(getKeyboard(nextState));
        InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
        inputMediaPhoto.setMedia(newPhoto, "profile_image");
        editMessageMedia.setMedia(inputMediaPhoto);
        return editMessageMedia;
    }

    public SendMessage getMenuMethod(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(getKeyboard(BotState.IN_MENU));
        sendMessage.setText("МЕНЮ");
        return sendMessage;
    }

    public SendMessage getSendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId.toString());
        return sendMessage;
    }

    private InlineKeyboardMarkup getKeyboard(BotState state) {
        switch (state) {
            case SEARCHING: return keyboardService.getSearchingKeyboard();
            case VIEWING: return keyboardService.getFavoritesKeyboard();
            default: return keyboardService.getInMenuKeyboard();
        }
    }

}
