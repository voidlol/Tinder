package ru.liga.botapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.botapi.TinderBot;

@RestController
@AllArgsConstructor
public class WebhookController {

    private final TinderBot tinderBot;

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
