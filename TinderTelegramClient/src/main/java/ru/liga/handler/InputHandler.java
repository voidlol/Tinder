package ru.liga.handler;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;

public interface InputHandler {

    String DELETE_URL = "http://localhost:9090/delete";

    BotApiMethod<?> handle(Message message);

    BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery);

    BotState getBotState();

    default void deleteMessage(Long chatId, Integer messageId, RestTemplate restTemplate) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(messageId);
        HttpEntity<DeleteMessage> entity = new HttpEntity<>(deleteMessage);
        deleteMessage.setChatId(chatId.toString());
        restTemplate.postForObject(DELETE_URL, entity, DeleteMessage.class);
    }
}
