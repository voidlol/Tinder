package ru.liga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.liga.client.cache.UserDetailsCache;
import ru.liga.domain.User;

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
