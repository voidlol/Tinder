package ru.liga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TinderClient {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TinderClient.class);
    }
}
