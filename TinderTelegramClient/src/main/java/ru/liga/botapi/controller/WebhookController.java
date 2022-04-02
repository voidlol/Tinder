package ru.liga.botapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.botapi.TinderBot;
import ru.liga.botapi.TinderBot2;
import ru.liga.domain.FileWrapper;
import ru.liga.keyboards.KeyboardService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@AllArgsConstructor
public class WebhookController {

    private final TinderBot2 tinderBot;

//    @PostMapping("/")
//    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
//        return tinderBot.onWebhookUpdateReceived(update);
//    }

    @PostMapping("/execute/change")
    public void changeMessage(@RequestBody EditMessageText method) {
        try {
            tinderBot.execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/execute/popup")
    public void popup(@RequestBody AnswerCallbackQuery method) {
        try {
            tinderBot.execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
