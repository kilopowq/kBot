package com.kilopo.command;

import com.kilopo.exchange.rate.Rates;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import static com.kilopo.Constants.CURRENCIES;
import static com.kilopo.Constants.EXCHANGE_RATES_URL;

public class ExchangeRateCommand extends BotCommand {
    private static final String COMMAND_NAME = "rate";
    private static final String COMMAND_DESCRIPTION = "Exchange rate";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public ExchangeRateCommand() {
        super(COMMAND_NAME, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        try {
            Rates rates = Unirest.get(EXCHANGE_RATES_URL)
                    .queryString("date", DATE_FORMAT.format(new Date()))
                    .asObject(Rates.class).getBody();
            ratesFormat(rates);
            sendMessage(chat, formatMessage(rates), absSender);
            System.out.println();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

    private void ratesFormat(Rates rates) {
        rates.getExchangeRate().removeIf(rate -> !CURRENCIES.contains(rate.getCurrency()));
        rates.getExchangeRate().sort(Comparator.comparingInt(left -> CURRENCIES.indexOf(left.getCurrency())));
    }

    private String formatMessage(Rates rates) {

        StringBuilder messageBuilder = new StringBuilder(String.format("%s%15s%15s%17s", "Валюта", "Покупка ПБ", "Продаж ПБ", "НБУ"));


        rates.getExchangeRate().forEach(rate -> messageBuilder
                .append("\n")
                .append(String.format("%s%23s%25s%25s",
                        rate.getCurrency(),
                        new DecimalFormat("#00.00").format(rate.getPurchaseRate()),
                        new DecimalFormat("#00.00").format(rate.getSaleRate()),
                        new DecimalFormat("#00.00").format(rate.getPurchaseRateNB())
                )));


        return messageBuilder.toString();
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
