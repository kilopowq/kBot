package com.kilopo.football;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Score {

    public Score() {
    }

    private String winner;
    private String duration;
    private Result fullTime;
    private Result halfTime;
    private Result extraTime;
    private Result penalties;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Result getFullTime() {
        return fullTime;
    }

    public void setFullTime(Result fullTime) {
        this.fullTime = fullTime;
    }

    public Result getHalfTime() {
        return halfTime;
    }

    public void setHalfTime(Result halfTime) {
        this.halfTime = halfTime;
    }

    public Result getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(Result extraTime) {
        this.extraTime = extraTime;
    }

    public Result getPenalties() {
        return penalties;
    }

    public void setPenalties(Result penalties) {
        this.penalties = penalties;
    }
}
