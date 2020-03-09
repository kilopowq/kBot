package com.kilopo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@Configuration
public class MainClass {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(MainClass.class, args);
    }

}
