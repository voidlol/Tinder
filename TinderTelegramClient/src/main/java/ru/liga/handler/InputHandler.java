package ru.liga.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;

public interface InputHandler {

    BotApiMethod<?> handle(Message message);

    BotState getBotState();
}
