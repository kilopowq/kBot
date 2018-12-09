package com.kilopo.command;

import com.kilopo.Constants;
import com.kilopo.CustomObjectMapper;
import com.kilopo.football.MatchDay;
import com.kilopo.football.Status;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;

import static com.kilopo.Constants.FOOTBALL_TOKEN;
import static com.kilopo.Constants.FOOTBALL_URL;

public class FootballCommand extends BotCommand {
    private static final String TOP_LEAGUES = "2001,2021,2014,2002,2019,2015,2000,2018";
    private static final String ALL_LEAGUES = "all";

    public FootballCommand(String league, String description) {
        super(league, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        sendMessage(chat, createScedule(getMatchday()), absSender);
    }

    private GetRequest getFootballURL() {
        return Unirest.get(FOOTBALL_URL)
                .header("accept", "application/json")
                .header("X-Auth-Token", FOOTBALL_TOKEN);
    }

    private HttpRequest getAllTodayMatchesURL() {
        return getFootballURL().queryString("competitions", TOP_LEAGUES);
    }

    private HttpRequest getTodaymathesByLeagueURL(String leagueCode) {
        return getFootballURL().queryString("competitions", leagueCode);
    }

    private String createScedule(MatchDay matchDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
        StringBuilder message = new StringBuilder();

        if (matchDay.getCompetition() != null) {
            message.append(matchDay.getCompetition().getName());
        }

        if (matchDay.getMatches().isEmpty()) {
            message.append("There is no matches for today (").append(getDescription()).append(").");
        }

        matchDay.getMatches().forEach(match -> {
            message
                    .append("\n")
                    .append("\n")
                    .append(dateFormat.format(match.getUtcDate()))
                    .append(" ")
                    .append(match.getHomeTeam().getName());

            if (match.getStatus().equals(Status.LIVE) || match.getStatus().equals(Status.FINISHED) || match.getStatus().equals(Status.IN_PLAY)) {
                message.append(" ")
                        .append(match.getScore().getFullTime().getHomeTeam())
                        .append(" - ")
                        .append(match.getScore().getFullTime().getAwayTeam())
                        .append(" ");
            } else {
                message.append(" - ");
            }

            message
                    .append(match.getAwayTeam().getName());

            if (match.getCompetition() != null) {
                message.append("\t |").append(match.getCompetition().getName());
            }
        });


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

    private MatchDay getMatchday() {

        HttpResponse<MatchDay> matchDay = null;
        try {
            if (this.getCommandIdentifier().equals(ALL_LEAGUES)) {
                matchDay = getAllTodayMatchesURL().asObject(MatchDay.class);
            } else {
                matchDay = getTodaymathesByLeagueURL(Constants.LEAGUES_CODES.get(this.getCommandIdentifier())).asObject(MatchDay.class);
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return matchDay != null ? matchDay.getBody() : new MatchDay();
    }
}
