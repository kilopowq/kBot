package com.kilopo.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherItem {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @JsonProperty("dt_txt")
    private Date date;

    @JsonProperty("main")
    private Main mainDescription;

    private Weather weather;

    private Wind wind;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date) {
        try {
            this.date = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Main getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(Main mainDescription) {
        this.mainDescription = mainDescription;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        if (weather.length != 0) {
            this.weather = weather[0];
        }
    }


    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
