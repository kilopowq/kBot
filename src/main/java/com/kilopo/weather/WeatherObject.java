package com.kilopo.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObject {

    @JsonProperty("list")
    private List<WeatherItem> weatherItemList;

    public List<WeatherItem> getWeatherItemList() {
        return weatherItemList;
    }

    public void setWeatherItemList(List<WeatherItem> weatherItemList) {
        this.weatherItemList = weatherItemList;
    }
}
