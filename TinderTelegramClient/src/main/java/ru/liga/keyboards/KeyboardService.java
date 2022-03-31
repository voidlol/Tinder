package ru.liga.keyboards;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.liga.domain.SexType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class KeyboardService {
    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow rowRegistrationOrLogin = new KeyboardRow();
        rowRegistrationOrLogin.add(new KeyboardButton(ButtonNameEnum.REGISTRATION_BUTTON.getButtonName()));
        rowRegistrationOrLogin.add(new KeyboardButton(ButtonNameEnum.LOGIN_BUTTON.getButtonName()));

        KeyboardRow rowMenu = new KeyboardRow();
        rowMenu.add(new KeyboardButton(ButtonNameEnum.SEARCH_BUTTON.getButtonName()));
        rowMenu.add(new KeyboardButton(ButtonNameEnum.FAVORITES_BUTTON.getButtonName()));
        rowMenu.add(new KeyboardButton(ButtonNameEnum.PROFILE_BUTTON.getButtonName()));

        KeyboardRow rowSearch = new KeyboardRow();
        rowSearch.add(new KeyboardButton(ButtonNameEnum.LEFT_BUTTON.getButtonName()));
        rowSearch.add(new KeyboardButton(ButtonNameEnum.RIGHT_BUTTON.getButtonName()));
        rowSearch.add(new KeyboardButton(ButtonNameEnum.BACK_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(rowRegistrationOrLogin);
        keyboard.add(rowMenu);
        keyboard.add(rowSearch);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getMySexKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton maleButton = createInlineButton(SexType.MALE.getTranslatedName(), String.valueOf(SexType.MALE));
        InlineKeyboardButton femaleButton = createInlineButton(SexType.FEMALE.getTranslatedName(), String.valueOf(SexType.FEMALE));
        List<InlineKeyboardButton> buttonsRow = createButtonsRow(maleButton, femaleButton);
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

    private List<InlineKeyboardButton> createButtonsRow(InlineKeyboardButton... buttons) {
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
        keyboard.setOneTimeKeyboard(true);
        return keyboard;
    }
}
