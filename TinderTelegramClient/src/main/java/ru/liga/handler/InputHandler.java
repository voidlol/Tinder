package ru.liga.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;

public interface InputHandler {

    SendMessage handle(Message message);
    BotState getBotState();
}
