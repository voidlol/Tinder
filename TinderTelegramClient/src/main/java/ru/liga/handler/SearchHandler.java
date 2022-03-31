package ru.liga.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.botstate.BotState;
import ru.liga.cache.UserSessionCache;
import ru.liga.client.profile.ProfileClient;

@Component
public class SearchHandler implements InputHandler {

    private final ProfileClient profileClient;
    private final UserSessionCache userSessionCache;

    @Autowired
    public SearchHandler(ProfileClient profileClient, UserSessionCache userSessionCache) {
        this.profileClient = profileClient;
        this.userSessionCache = userSessionCache;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processInputMessage(message);
    }

    @Override
    public BotState getBotState() {
        return BotState.SEARCHING;
    }

    private BotApiMethod<?> processInputMessage(Message message) {
        return new SendMessage();
    }
}
