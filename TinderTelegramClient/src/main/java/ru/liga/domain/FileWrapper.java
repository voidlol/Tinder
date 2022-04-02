package ru.liga.domain;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
public class FileWrapper {

    private final String chatId;
    private final String filePath;
    private final InlineKeyboardMarkup keyboard;

}
