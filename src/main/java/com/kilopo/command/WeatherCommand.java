package com.kilopo.command;

import com.kilopo.weather.WeatherItem;
import com.kilopo.weather.WeatherObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.kilopo.Constants.WEATHER_QUERY_PARAMS;
import static com.kilopo.Constants.WEATHER_URL;


public class WeatherCommand extends BotCommand {
    private final Integer daysQuantity;

    public WeatherCommand(String commandIdentifier, String description, Integer daysQuantity) {
        super(commandIdentifier.concat(daysQuantity.toString()), description);
        this.daysQuantity = daysQuantity;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            WeatherObject weatherObject = Unirest.get(WEATHER_URL)
                    .queryString(WEATHER_QUERY_PARAMS)
                    .asObject(WeatherObject.class)
                    .getBody();
            sendMessage(chat, createMessage(getWeatherItemsForDaysQuatity(weatherObject)), absSender);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private List<WeatherItem> getWeatherItemsForDaysQuatity(WeatherObject weatherObject) {
        Date maxDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(maxDate);
        c.add(Calendar.DATE, daysQuantity);
        return weatherObject.getWeatherItemList()
                .stream()
                .filter(weatherItem -> weatherItem.getDate().before(c.getTime()))
                .collect(Collectors.toList());
    }

    private String createMessage(List<WeatherItem> weatherItemList) {
        SimpleDateFormat slf = new SimpleDateFormat("dd.MM HH:mm");
        StringBuilder message = new StringBuilder();

        weatherItemList.forEach(weatherItem ->
                message.append(slf.format(weatherItem.getDate()))
                        .append(" від ")
                        .append(weatherItem.getMainDescription().getMinimumTemperature())
                        .append(" до ")
                        .append(weatherItem.getMainDescription().getMaximumTemperature())
                        .append(", ")
                        .append(weatherItem.getWeather().getDescription())
                        .append("\n")
        );
        return message.toString();
    }

    private void sendMessage(Chat chat, String text, AbsSender absSender) {
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
