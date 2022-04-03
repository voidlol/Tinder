package ru.liga.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.liga.config.QueryData;
import ru.liga.domain.SexType;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final TextMessageService textMessageService;

    public InlineKeyboardMarkup getInMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton searchButton = createInlineButton(textMessageService.getText("button.menu.search"), QueryData.SEARCH);
        InlineKeyboardButton favoritesButton = createInlineButton(textMessageService.getText("button.menu.favorites"), QueryData.FAVORITES);
        InlineKeyboardButton profileButton = createInlineButton(textMessageService.getText("button.menu.profile"), QueryData.PROFILE);
        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(searchButton, profileButton, favoritesButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMySexKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton maleButton = createInlineButton(textMessageService.getText("button.sexType.male"), String.valueOf(SexType.MALE));
        InlineKeyboardButton femaleButton = createInlineButton(textMessageService.getText("button.sexType.female"), String.valueOf(SexType.FEMALE));
        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(maleButton, femaleButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getLookingForKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton maleButton = createInlineButton(textMessageService.getText("button.lookingFor.male"), String.valueOf(SexType.MALE));
        InlineKeyboardButton femaleButton = createInlineButton(textMessageService.getText("button.lookingFor.female"), String.valueOf(SexType.FEMALE));
        InlineKeyboardButton allButton = createInlineButton(textMessageService.getText("button.lookingFor.all"), QueryData.ALL);
        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(maleButton, femaleButton, allButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> createInlineButtonsRow(InlineKeyboardButton... buttons) {
        return new ArrayList<>(List.of(buttons));
    }

    private InlineKeyboardButton createInlineButton(String text, String data) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(data);
        return button;
    }

    public ReplyKeyboardMarkup getWelcomeKeyboard() {
        KeyboardRow rowRegistrationOrLogin = new KeyboardRow();
        rowRegistrationOrLogin.add(new KeyboardButton(textMessageService.getText("button.registration")));
        rowRegistrationOrLogin.add(new KeyboardButton(textMessageService.getText("button.login")));
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setKeyboard(List.of(rowRegistrationOrLogin));
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        return keyboard;
    }

    public InlineKeyboardMarkup getSearchingKeyboard() {
        InlineKeyboardButton nextButton = createInlineButton(textMessageService.getText("button.next"), QueryData.NEXT);
        InlineKeyboardButton menuButton = createInlineButton(textMessageService.getText("button.menu"), QueryData.MENU);
        InlineKeyboardButton likeButton = createInlineButton(textMessageService.getText("button.search.like"), QueryData.LIKE);

        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(nextButton, menuButton, likeButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup getFavoritesKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton prevButton = createInlineButton(textMessageService.getText("button.favorites.prev"), QueryData.PREV);
        InlineKeyboardButton nextButton = createInlineButton(textMessageService.getText("button.next"), QueryData.NEXT);
        InlineKeyboardButton menuButton = createInlineButton(textMessageService.getText("button.menu"), QueryData.MENU);
        InlineKeyboardButton dislikeButton = createInlineButton(textMessageService.getText("button.favorites.dislike"), QueryData.DISLIKE);

        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(prevButton, nextButton, menuButton, dislikeButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
