package ru.liga.handler;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;

public interface InputHandler {


    BotApiMethod<?> handle(Message message);

    BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery);

    BotState getBotState();

    default void changeMessage(RestTemplate restTemplate, EditMessageText editMessageText) {
        final String DELETE_URL = "http://localhost:9090/change";
        HttpEntity<EditMessageText> entity = new HttpEntity<>(editMessageText);
        restTemplate.postForObject(DELETE_URL, entity, EditMessageText.class);
    }

    default AnswerCallbackQuery sendCallbackQuery(String text, CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setText(text);
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        return answerCallbackQuery;
    }
}
