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
    private String newMessageText;

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
        checkLus(update);
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

            if ((newMessageText = checkName(text)) != null) {
                newMessageText = newMessageText + " пезда, я не знаю шо дєлать.";
            } else {
                newMessageText = text + " пезда, я не знаю шо дєлать.";
            }
            sendMessage(newMessageText, update);
        }
    }

    private void checkLus(Update update) {
        newMessageText = checkName(update.getMessage().getText());
        if (newMessageText != null) {
            newMessageText = "Ви мали на увазі " + newMessageText + " ?";
            sendMessage(newMessageText, update);
        }
    }

    private String checkName(String message) {
        message = message.toLowerCase();
        String[] words = message.split(" ");

        if (Arrays.stream(words).anyMatch(word -> word.contains("лисий") || word.contains("лиcий"))) {
            return "Мар'ян";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисого")
                || word.contains("лиcого") || word.contains("лисoго") || word.contains("лисоrо") || word.contains("лисогo")
                || word.contains("лиcoго") || word.contains("лиcоrо") || word.contains("лиcогo")
                || word.contains("лисorо") || word.contains("лисoгo")
                || word.contains("лисоro")
                || word.contains("лиcorо") || word.contains("лиcoгo") || word.contains("лиcоro")
                || word.contains("лиcoro"))) {
            return "Мар'яна";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисому") || word.contains("лиcому") || word.contains("лисoму") || word.contains("лисоmу") || word.contains("лисомy")
                || word.contains("лиcoму") || word.contains("лиcоmу") || word.contains("лиcомy")
                || word.contains("лисomу") || word.contains("лисoмy")
                || word.contains("лисоmy")
                || word.contains("лиcomу") || word.contains("лиcoмy") || word.contains("лиcоmy")
                || word.contains("лисomy"))) {
            return "Мар'яну";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисим") || word.contains("лиcим") || word.contains("лисиm") || word.contains("лиcиm"))) {
            return "Мар'яном";
        } else
            return null;
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