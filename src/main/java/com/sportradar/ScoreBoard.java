package com.sportradar;

import java.util.List;

/**
 *
 */
public interface ScoreBoard {
    /**
     *
     */
    void startGame(String homeTeam, String awayTeam);

    /**
     *
     */
    void finishGame();

    /**
     *
     */
    void updateScore(Integer newHomeTeamScore, Integer newAwayTeamScore);

    /**
     *
     */
    List<Object> getGamesSummary();
}
