package com.kilopo;

import com.kilopo.command.FootballCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

import static com.kilopo.Constants.BOT_NAME;
import static com.kilopo.Constants.BOT_TOKEN;

public class KBot extends TelegramLongPollingCommandBot {
    private String newMessageText;

    KBot() {
        super(BOT_NAME);
        register(new FootballCommand("all", "All leagues"));
        register(new FootballCommand("apl", "Premiere League"));
        register(new FootballCommand("cl", "Champion League"));
        register(new FootballCommand("la", "La Liga"));
        register(new FootballCommand("bund", "Bundesliga"));
        register(new FootballCommand("sa", "Serie A"));
        register(new FootballCommand("l1", "Ligue 1"));
        register(new FootballCommand("wc", "World Cup"));
        register(new FootballCommand("ec", "Euro"));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        checkLus(update);
        hzShoDelat(update);
        //writeToConsolNotFedorka(update);
    }

    private void hzShoDelat(Update update) {
        String[] separators = {" ", ".", "/", ",", "|", "_"};
        String[] lastChars = {"і", "у", "ю", "ї"};
        String[] lastTwoChars = {"ам"};
        String text = update.getMessage().getText();
        String lastChar = text.substring(text.length() - 1);
        String lastTwoChar = text.substring(text.length() - 2);

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

        if (Arrays.stream(words).anyMatch(word -> word.contains("лисий"))) {
            return "Мар'ян";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисого"))) {
            return "Мар'яна";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисому"))) {
            return "Мар'яну";
        } else if (Arrays.stream(words).anyMatch(word -> word.contains("лисим"))) {
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

    private void writeToConsolNotFedorka(Update update) {
        if (update.getMessage().getChat().getTitle() == null || !update.getMessage().getChat().getTitle().equals("Федорка v2.0")) {
            System.out.println("-" + update.getMessage().getChat().getTitle() + " | " + update.getMessage().getText() + " | " +
                    update.getMessage().getFrom().getUserName() + "(" + update.getMessage().getFrom().getFirstName() + " " +
                    update.getMessage().getFrom().getLastName() + ")");
        }
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}