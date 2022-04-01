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

    default void executeMethod(RestTemplate restTemplate, BotApiMethod<?> method) {
        final String DELETE_URL = "http://localhost:9090/execute";
        String methodType = "/popup";
        if (method instanceof EditMessageText) {
            methodType = "/change";
        }
        HttpEntity<BotApiMethod<?>> entity = new HttpEntity<>(method);
        restTemplate.postForObject(DELETE_URL + methodType, entity, Void.class);
    }

    default AnswerCallbackQuery sendCallbackQuery(String text, CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setText(text);
        answerCallbackQuery.setShowAlert(false);
        answerCallbackQuery.setCallbackQueryId(callbackQuery.getId());
        return answerCallbackQuery;
    }
}
