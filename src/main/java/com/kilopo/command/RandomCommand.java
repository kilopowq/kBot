package com.kilopo.command;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.kilopo.BotUtils.checkName;
import static com.kilopo.BotUtils.sendMessage;
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
        Optional<String> lys = members.stream().filter(p -> nonNull(checkName(p))).findFirst();
        if (lys.isPresent()) {
            String text = "Ви мали на увазі " + checkName(lys.get()) + " ?";
            sendMessage(absSender, text, chat);
        } else {
            Random rand = new Random();
            String randomElement = members.get(rand.nextInt(members.size()));
            sendMessage(absSender, randomElement, chat);
        }
    }
}
