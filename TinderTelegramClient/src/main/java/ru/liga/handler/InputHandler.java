package ru.liga.handler;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;

import java.util.List;

public interface InputHandler {


    List<PartialBotApiMethod<?>> handle(Message message);

    List<PartialBotApiMethod<?>> handleCallBack(CallbackQuery callbackQuery);

    BotState getBotState();
}
