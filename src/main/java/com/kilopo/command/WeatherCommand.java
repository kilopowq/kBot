package com.kilopo.command;

import com.kilopo.weather.WeatherItem;
import com.kilopo.weather.WeatherObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.kilopo.BotUtils.sendMessage;
import static com.kilopo.Constants.WEATHER_QUERY_PARAMS;
import static com.kilopo.Constants.WEATHER_URL;


public class WeatherCommand extends BotCommand {
    private Integer daysQuantity = 0;

    public WeatherCommand(String commandIdentifier, String description, Integer daysQuantity) {
        super(commandIdentifier.concat(daysQuantity.toString()), description);
        this.daysQuantity = daysQuantity;
    }

    public WeatherCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        try {
            WeatherObject weatherObject = Unirest.get(WEATHER_URL)
                    .queryString(WEATHER_QUERY_PARAMS)
                    .asObject(WeatherObject.class)
                    .getBody();
            sendMessage(absSender, createMessage(getWeatherItemsForDaysQuatity(weatherObject)), chat);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private List<WeatherItem> getWeatherItemsForDaysQuatity(WeatherObject weatherObject) {
        Date maxDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(maxDate);
        if (daysQuantity != 0) {
            c.add(Calendar.DATE, daysQuantity);
        } else {
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DATE);
            c.set(year, month, day + 1, 0, 0, 0);
        }
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
                        .append("   ")
                        .append((int) Math.round(weatherItem.getMainDescription().getTemperature()))
                        .append("°,  ")
                        .append(weatherItem.getWeather().getDescription())
                        .append("\n")
        );
        return message.toString();
    }


}
