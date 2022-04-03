package ru.liga.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TextMessageService {

    private final MessageSource messageSource;

    public TextMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getText(String code) {
        return messageSource.getMessage(code, null, Locale.ROOT);
    }
}
