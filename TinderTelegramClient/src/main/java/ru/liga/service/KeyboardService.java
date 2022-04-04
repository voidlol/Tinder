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
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final TextMessageService textMessageService;

    public InlineKeyboardMarkup getInMenuKeyboard() {
        InlineKeyboardButton searchButton = createInlineButton(textMessageService.getText("button.menu.search"), QueryData.SEARCH);
        InlineKeyboardButton favoritesButton = createInlineButton(textMessageService.getText("button.menu.favorites"), QueryData.FAVORITES);
        InlineKeyboardButton profileButton = createInlineButton(textMessageService.getText("button.menu.profile"), QueryData.PROFILE);
        InlineKeyboardButton logoutButton = createInlineButton(textMessageService.getText("button.menu.logout"), QueryData.LOGOUT);

        return getInlineKeyboardMarkup(searchButton, favoritesButton, profileButton, logoutButton);
    }

    private InlineKeyboardMarkup getInlineKeyboardMarkup(InlineKeyboardButton... buttons) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(buttons);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMySexKeyboard() {
        InlineKeyboardButton maleButton = createInlineButton(textMessageService.getText("button.sexType.male"), String.valueOf(SexType.MALE));
        InlineKeyboardButton femaleButton = createInlineButton(textMessageService.getText("button.sexType.female"), String.valueOf(SexType.FEMALE));

        return getInlineKeyboardMarkup(maleButton, femaleButton);
    }

    public InlineKeyboardMarkup getLookingForKeyboard() {
        InlineKeyboardButton maleButton = createInlineButton(textMessageService.getText("button.lookingFor.male"), String.valueOf(SexType.MALE));
        InlineKeyboardButton femaleButton = createInlineButton(textMessageService.getText("button.lookingFor.female"), String.valueOf(SexType.FEMALE));
        InlineKeyboardButton allButton = createInlineButton(textMessageService.getText("button.lookingFor.all"), QueryData.ALL);

        return getInlineKeyboardMarkup(maleButton, femaleButton, allButton);
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
        keyboard.setKeyboard(Collections.singletonList(rowRegistrationOrLogin));
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        return keyboard;
    }

    public InlineKeyboardMarkup getSearchingKeyboard() {
        InlineKeyboardButton nextButton = createInlineButton(textMessageService.getText("button.next"), QueryData.NEXT);
        InlineKeyboardButton menuButton = createInlineButton(textMessageService.getText("button.menu"), QueryData.MENU);
        InlineKeyboardButton likeButton = createInlineButton(textMessageService.getText("button.search.like"), QueryData.LIKE);

        return getInlineKeyboardMarkup(nextButton, menuButton, likeButton);
    }


    public InlineKeyboardMarkup getFavoritesKeyboard() {
        InlineKeyboardButton prevButton = createInlineButton(textMessageService.getText("button.favorites.prev"), QueryData.PREV);
        InlineKeyboardButton nextButton = createInlineButton(textMessageService.getText("button.next"), QueryData.NEXT);
        InlineKeyboardButton menuButton = createInlineButton(textMessageService.getText("button.menu"), QueryData.MENU);
        InlineKeyboardButton dislikeButton = createInlineButton(textMessageService.getText("button.favorites.dislike"), QueryData.DISLIKE);

        return getInlineKeyboardMarkup(prevButton, nextButton, menuButton, dislikeButton);
    }

    public ReplyKeyboardMarkup getInitKeyboard() {
        KeyboardRow rowRegistrationOrLogin = new KeyboardRow();
        rowRegistrationOrLogin.add(new KeyboardButton("/start"));
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setKeyboard(Collections.singletonList(rowRegistrationOrLogin));
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        return keyboard;
    }
}
