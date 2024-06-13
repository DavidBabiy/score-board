package com.sportradar;

import java.util.List;

public interface ScoreBoard {
    void startGame(String homeTeamName, String awayTeamName);
    void finishGame();
    void updateScore(int homeTeamScore, int awayTeamScore);
    List<GameScoreDto> getGamesSummary();
}
