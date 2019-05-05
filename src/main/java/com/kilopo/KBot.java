package com.kilopo;

import com.kilopo.command.ExchangeRateCommand;
import com.kilopo.command.FootballCommand;
import com.kilopo.command.WeatherCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

import static com.kilopo.Constants.*;

public class KBot extends TelegramLongPollingCommandBot {

    KBot() {
        super(BOT_NAME);
        LEAGUES_NAMES.forEach((key, value) -> register(new FootballCommand(key, value)));
        register(new FootballCommand("all", "All leagues."));
        register(new ExchangeRateCommand());

        register(new WeatherCommand("w", "Weather for current day"));
        register(new WeatherCommand("w", "Weather for 24 hours", 1));
        register(new WeatherCommand("w", "Weather for 3 day", 3));
        register(new WeatherCommand("w", "Weather for 5 day", 5));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        hzShoDelat(update);
    }

    private void hzShoDelat(Update update) {
        String[] separators = {" ", ".", "/", ",", "|", "_"};
        String[] lastChars = {"і", "у", "ю", "ї"};
        String[] lastTwoChars = {"ам"};
        String text = update.getMessage().getText();
        String lastChar = text.substring(text.length() - 1);
        String lastTwoChar = null;
        if (text.length() > 1) {
            lastTwoChar = text.substring(text.length() - 2);
        }


        if (Arrays.stream(separators).noneMatch(text::contains)
                && (Arrays.stream(lastChars).anyMatch(lastChar::contains)
                || Arrays.stream(lastTwoChars).anyMatch(lastTwoChar::contains)
                || text.equals("їм")
        )

        ) {

            String newMessageText = text + " пезда, я не знаю шо дєлать.";
            sendMessage(newMessageText, update);
        }
    }

    private void sendMessage(String text, Update update) {
        SendMessage message = new SendMessage();

        message.setChatId(update.getMessage().getChatId());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}