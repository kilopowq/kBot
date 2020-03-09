package com.kilopo.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.kilopo.BotUtils.checkName;
import static java.util.Objects.nonNull;

public class RandomCommand extends BotCommand {
    private static final String COMMAND_NAME = "random";
    private static final String COMMAND_DESCRIPTION = "Random member";

    public RandomCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        List<String> members = Arrays.asList(strings);
        if(members.stream().anyMatch(p -> nonNull(checkName(p)))) {
           return;
        }
        Random rand = new Random();
        String randomElement = members.get(rand.nextInt(members.size()));
        sendMessage(chat, randomElement, absSender);
    }

    private void sendMessage(Chat chat, String text, AbsSender absSender) {
        SendMessage message = new SendMessage();

        message.setChatId(chat.getId());
        message.setText(text);
        message.enableHtml(true);

        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
