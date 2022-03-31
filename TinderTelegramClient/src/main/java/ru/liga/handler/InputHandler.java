package ru.liga.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;

public interface InputHandler {

    BotApiMethod<?> handle(Message message);

    BotApiMethod<?> handleCallBack(CallbackQuery callbackQuery);

    BotState getBotState();
}
