package com.sportradar;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class GameScore {
    private final int index;
    private final String homeTeamName;
    private final String awayTeamName;
    private int homeTeamScore;
    private int awayTeamScore;

    public GameScore(String homeTeamName, String awayTeamName, int index) {
        this.index = index;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    public void updateScore(int homeTeamScore, int awayTeamScore) {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }
}
