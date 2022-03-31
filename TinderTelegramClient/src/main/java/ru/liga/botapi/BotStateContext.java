package ru.liga.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.handler.InputHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private final Map<BotState, InputHandler> inputHandlers = new HashMap<>();

    public BotStateContext(List<InputHandler> handlers) {
        handlers.forEach(handler -> this.inputHandlers.put(handler.getBotState(), handler));
    }

    public BotApiMethod<?> processInputMessage(BotState botState, Message message) {
        return findInputHandler(botState).handle(message);
    }

    private InputHandler findInputHandler(BotState currentState) {
        if (isRegistration(currentState)) {
            return inputHandlers.get(BotState.REGISTER);
        }

        if (isProfileFilling(currentState)) {
            return inputHandlers.get(BotState.PROFILE_FILLING);
        }

        return inputHandlers.get(currentState);
    }

    private boolean isRegistration(BotState currentState) {
        switch (currentState) {
            case REGISTER_ASK_PASSWORD:
            case REGISTER_ASK_CONF_PASSWORD:
                return true;
            default:
                return false;
        }
    }

    private boolean isProfileFilling(BotState currentState) {
        switch (currentState) {
            case PROFILE_FILLING_ASK_NAME:
            case PROFILE_FILLING_ASK_DESCRIPTION:
            case PROFILE_FILLING_ASK_GENDER:
            case PROFILE_FILLING_ASK_LOOKING_FOR:
                return true;
            default:
                return false;
        }
    }

}
