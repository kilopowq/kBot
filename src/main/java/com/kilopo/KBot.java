package com.kilopo;

import com.kilopo.command.ExchangeRateCommand;
import com.kilopo.command.FootballCommand;
import com.kilopo.command.RandomCommand;
import com.kilopo.command.WeatherCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

import static com.kilopo.BotUtils.checkName;
import static com.kilopo.BotUtils.sendMessage;
import static com.kilopo.Constants.*;

@Component
public class KBot extends TelegramLongPollingCommandBot {
    private String newMessageText;

    KBot() {
        LEAGUES_NAMES.forEach((key, value) -> register(new FootballCommand(key, value)));
        register(new FootballCommand("all", "All leagues."));
        register(new ExchangeRateCommand());
        register(new RandomCommand());

        register(new WeatherCommand("w", "Weather for current day"));
        register(new WeatherCommand("w", "Weather for 24 hours", 1));
        register(new WeatherCommand("w", "Weather for 3 day", 3));
        register(new WeatherCommand("w", "Weather for 5 day", 5));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        checkLus(update);
        hzShoDelat(update);
        aleksiukYana(update);
    }

    private void aleksiukYana(Update update) {
        if (update.getMessage().getText().equalsIgnoreCase("алексюк яна")) {
            newMessageText = "З'їбала нахуй сука, шмара блять, шлюха нахуй з мого блять, з мої сторони їбана ти нахуй гнида на... \n Так, кого ще, блять, послати, їбані ви, нахуй, підараси, блять шлюхи нах бль...";
            sendMessage(this, newMessageText, update);
        }
    }


    private void hzShoDelat(Update update) {
        String[] separators = {" ", ".", "/", ",", "|", "_"};
        String[] lastChars = {"і", "у", "ю", "ї"};
        String[] lastTwoChars = {"ам"};
        String[] exceptions = {"юху", "внатурі", "взагалі", "всмислі", "аха", "ахах", "юхуу", "юхууу", "ахаха", "угу", "угуу", "воу", "воуу"};
        String text = update.getMessage().getText();
        String lastChar = text.substring(text.length() - 1);
        String lastTwoChar = null;
        if (text.length() > 1) {
            lastTwoChar = text.substring(text.length() - 2);
        }


        if (Arrays.stream(separators).noneMatch(text::contains)
                && Arrays.stream(exceptions).noneMatch(p -> p.equals(text.toLowerCase()))
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
            sendMessage(this, newMessageText, update);
        }
    }

    private void checkLus(Update update) {
        newMessageText = checkName(update.getMessage().getText());
        if (newMessageText != null) {
            newMessageText = "Ви мали на увазі " + newMessageText + " ?";
            sendMessage(this, newMessageText, update);
        }
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}