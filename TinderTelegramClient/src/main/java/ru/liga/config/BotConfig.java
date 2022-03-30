package ru.liga.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import ru.liga.botapi.TelegramFacade;
import ru.liga.botapi.TinderBot;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;

    @Bean
    public TinderBot createTinderBot(TelegramFacade telegramFacade) {
        DefaultBotOptions options = new DefaultBotOptions();

        options.setProxyHost(proxyHost);
        options.setProxyPort(proxyPort);
        options.setProxyType(proxyType);

        TinderBot tinderBot = new TinderBot(options, telegramFacade);
        tinderBot.setBotUsername(botUserName);
        tinderBot.setBotToken(botToken);
        tinderBot.setBotPath(webHookPath);

        return tinderBot;
    }

}
