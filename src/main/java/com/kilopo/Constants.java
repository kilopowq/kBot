package com.kilopo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public interface Constants {
    String BOT_NAME = "yahz_bot";
    String BOT_TOKEN = "659773170:AAG-H37w4EhaLIAqVpskAQgSky7R7MhUZyI";
    String FOOTBALL_TOKEN = "59b23acc0f2c464fa984dfcda4bf3146";
    String WEATHER_TOKEN = "a9b3142e7635db621d8d40f9bdbc0920";
    String EXCHANGE_RATES_URL = "https://api.privatbank.ua/p24api/exchange_rates?json";
    String FOOTBALL_URL = "https://api.football-data.org/v2/matches";
    String WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast";

    Map<String, String> LEAGUES_CODES = new ImmutableMap.Builder<String, String>()
            .put("cl", "2001")
            .put("apl", "2021")
            .put("la", "2014")
            .put("bund", "2002")
            .put("sa", "2019")
            .put("l1", "2015")
            .put("wc", "2000")
            .put("ec", "2018")
            .build();

    Map<String, String> LEAGUES_NAMES = new ImmutableMap.Builder<String, String>()
            .put("cl", "Champion League")
            .put("apl", "Premiere League")
            .put("la", "La Liga")
            .put("bund", "Bundesliga")
            .put("sa", "Serie A")
            .put("l1", "Ligue 1")
            .put("wc", "World Cup")
            .put("ec", "Euro")
            .build();

    List<String> CURRENCIES = new ImmutableList.Builder<String>()
            .add("USD")
            .add("EUR")
            .add("RUB")
            .add("PLZ")
            .build();

    Map<String, Object> WEATHER_QUERY_PARAMS = new ImmutableMap.Builder<String, Object>()
            .put("APIKEY", WEATHER_TOKEN)
            .put("q", "Lviv")
            .put("units", "Metric")
            .put("lang", "ua")
            .build();
}