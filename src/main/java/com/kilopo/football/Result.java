package com.kilopo.football;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    public Result() {
    }

    private Integer homeTeam;
    private Integer awayTeam;

    public Integer getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Integer homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Integer awayTeam) {
        this.awayTeam = awayTeam;
    }
}
