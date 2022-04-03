package ru.liga.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.liga.domain.SexType;
import ru.liga.keyboards.ButtonNameEnum;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {

    public InlineKeyboardMarkup getInMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton searchButton = createInlineButton(ButtonNameEnum.SEARCH_BUTTON.getButtonName(), "SEARCH");
        InlineKeyboardButton favoritesButton = createInlineButton(ButtonNameEnum.FAVORITES_BUTTON.getButtonName(), "FAVORITES");
        InlineKeyboardButton profileButton = createInlineButton(ButtonNameEnum.PROFILE_BUTTON.getButtonName(), "PROFILE");
        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(searchButton, profileButton, favoritesButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMySexKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton maleButton = createInlineButton(SexType.MALE.getTranslatedName(), String.valueOf(SexType.MALE));
        InlineKeyboardButton femaleButton = createInlineButton(SexType.FEMALE.getTranslatedName(), String.valueOf(SexType.FEMALE));
        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(maleButton, femaleButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getLookingForKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = getMySexKeyboard();
        inlineKeyboardMarkup.getKeyboard().get(0).add(createInlineButton("Всех", "ALL"));
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
        rowRegistrationOrLogin.add(new KeyboardButton(ButtonNameEnum.REGISTRATION_BUTTON.getButtonName()));
        rowRegistrationOrLogin.add(new KeyboardButton(ButtonNameEnum.LOGIN_BUTTON.getButtonName()));
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setKeyboard(List.of(rowRegistrationOrLogin));
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(true);
        return keyboard;
    }

    public InlineKeyboardMarkup getSearchingKeyboard() {
        InlineKeyboardButton nextButton = createInlineButton("Следующая", "NEXT");
        InlineKeyboardButton menuButton = createInlineButton("В меню", "MENU");
        InlineKeyboardButton likeButton = createInlineButton("Лайкнуть", "LIKE");

        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(nextButton, menuButton, likeButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }


    public InlineKeyboardMarkup getFavoritesKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton prevButton = createInlineButton("Предыдущая", "PREV");
        InlineKeyboardButton nextButton = createInlineButton("Следующая", "NEXT");
        InlineKeyboardButton menuButton = createInlineButton("В меню", "MENU");
        InlineKeyboardButton dislikeButton = createInlineButton("Убрать лайк", "DISLIKE");

        List<InlineKeyboardButton> buttonsRow = createInlineButtonsRow(prevButton, nextButton, menuButton, dislikeButton);
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(buttonsRow);
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;
    }
}
