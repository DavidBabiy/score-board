package com.sportradar;

import java.time.ZonedDateTime;

/**
 *  Object that responsible for operations on the game and its score
 */
public class GameScore {
    private final ZonedDateTime startDate;
    private final String homeTeamName;
    private final String awayTeamName;
    private byte homeTeamScore;
    private byte awayTeamScore;

    public GameScore(String homeTeamName, String awayTeamName) {
        this.startDate = ZonedDateTime.now();
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    public void updateScore(byte homeTeamScore, int awayTeamScore) {
        // TODO: David: Implement
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    @Override
    public String toString() {
        return homeTeamName + " " + homeTeamScore + " - " + awayTeamName + " " + awayTeamScore;
    }
}
