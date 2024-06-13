package com.sportradar;

public record GameScoreDto(String homeTeamName, int homeTeamScore, String awayTeamName, int awayTeamScore) {
    public static GameScoreDto of(GameScore gameScore) {
        return new GameScoreDto(
                gameScore.getHomeTeamName(),
                gameScore.getHomeTeamScore(),
                gameScore.getAwayTeamName(),
                gameScore.getAwayTeamScore()
        );
    }

    @Override
    public String toString() {
        return homeTeamName + " (" + homeTeamScore + ") - " + awayTeamName + " (" + awayTeamScore + ")";
    }
}