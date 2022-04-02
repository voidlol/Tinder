package ru.liga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

//    private String webHookPath;
//    private String userName;
//    private String botToken;
//
//    @Bean
//    public TinderBot createTinderBot(TelegramFacade telegramFacade) {
//        SetWebhook setWebhook = SetWebhook.builder().url(webHookPath).build();
//
//        TinderBot tinderBot = new TinderBot(setWebhook, telegramFacade);
//        tinderBot.setBotUsername(userName);
//        tinderBot.setBotToken(botToken);
//
//        return tinderBot;
//    }

}
