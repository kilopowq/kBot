package com.kilopo;

import com.mashape.unirest.http.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@Configuration
public class MainClass {

    public static void main(String[] args) {
        Unirest.setObjectMapper(new CustomObjectMapper());
        ApiContextInitializer.init();
        SpringApplication.run(MainClass.class, args);
    }

}
