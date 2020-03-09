package com.kilopo;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class BotUtils {

    public static String checkName(String message) {
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

    public static void sendMessage(AbsSender absSender, String text, Update update) {
        SendMessage message = new SendMessage();

        message.setChatId(update.getMessage().getChatId());
        message.setText(text);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(AbsSender absSender, String text, Chat chat) {
        SendMessage message = new SendMessage();

        message.setChatId(chat.getId());
        message.setText(text);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
