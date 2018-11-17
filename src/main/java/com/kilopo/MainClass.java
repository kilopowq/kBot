package com.kilopo;

import com.mashape.unirest.http.Unirest;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MainClass {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        Unirest.setObjectMapper(new CustomObjectMapper());

        try {
            telegramBotsApi.registerBot(new KBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
