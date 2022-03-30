package ru.liga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.liga.cache.UserDetailsCache;
import ru.liga.client.login.LoginClient;
import ru.liga.domain.Profile;
import ru.liga.domain.SexType;
import ru.liga.domain.User;

import java.util.Set;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(App.class);
        UserDetailsCache bean = run.getBean(UserDetailsCache.class);
        User user = new User();
        user.setPassword("testpassword2");
        user.setId(5l);
    }
}
