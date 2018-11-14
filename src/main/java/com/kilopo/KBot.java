package com.kilopo;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class KBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "yahz_bot";
    private static final String BOT_TOKEN = "659773170:AAG-H37w4EhaLIAqVpskAQgSky7R7MhUZyI";
    private String newMessageText;

    @Override
    public void onUpdateReceived(Update update) {
        checkLus(update);
        hzShoDelat(update);
        //writeToConsolNotFedorka(update);
    }

    private void hzShoDelat(Update update) {
        String[] separators = {" ", ".", "/", ",", "|", "_"};
        String[] lastChars = {"і", "у", "ю", "ї"};
        String text = update.getMessage().getText();
        String lastChar = text.substring(text.length() - 1);

        if (Arrays.stream(separators).noneMatch(text::contains)
                && Arrays.stream(lastChars).anyMatch(lastChar::contains)
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

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}