package com.kilopo;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public interface Constants {
    String BOT_NAME = "yahz_bot";
    String BOT_TOKEN = "659773170:AAG-H37w4EhaLIAqVpskAQgSky7R7MhUZyI";
    String FOOTBALL_TOKEN = "59b23acc0f2c464fa984dfcda4bf3146";

    Map<String, String> leagues = new ImmutableMap.Builder<String, String>()
            .put("cl", "2001")
            .put("apl", "2021")
            .put("la", "2014")
            .put("bund", "2002")
            .put("sa", "2019")
            .put("l1", "2015")
            .put("wc", "2000")
            .put("ec", "2018")
            .build();
}


