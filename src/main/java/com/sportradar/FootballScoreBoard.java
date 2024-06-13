package com.sportradar;

import com.sportradar.exception.InvalidArgumentsException;
import com.sportradar.exception.InvalidOperationException;

import java.util.*;

public class FootballScoreBoard implements ScoreBoard {
    private final Set<GameScore> gameScores = new TreeSet<>(
            Comparator.comparingInt(GameScore::getTotalScore)
                    .thenComparingInt(GameScore::getIndex)
                    .reversed()
    );
    private GameScore onGoingGameScore;

    @Override
    public void startGame(String homeTeamName, String awayTeamName) {
        if (Objects.nonNull(onGoingGameScore)) {
            throw new InvalidOperationException("Unfinished game exists");
        }
        if (Objects.isNull(homeTeamName) || homeTeamName.isEmpty()
                || Objects.isNull(awayTeamName) || awayTeamName.isEmpty()) {
            throw new InvalidArgumentsException("Team name is required");
        }
        onGoingGameScore = new GameScore(homeTeamName, awayTeamName, gameScores.size());
    }

    @Override
    public void finishGame() {
        if (Objects.isNull(onGoingGameScore)) {
            throw new InvalidOperationException("Game was not found");
        }
        gameScores.add(onGoingGameScore);
        onGoingGameScore = null;
    }

    @Override
    public void updateScore(int homeTeamScore, int awayTeamScore) {
        if (Objects.isNull(onGoingGameScore)) {
            throw new InvalidOperationException("Game was not found");
        }
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new InvalidArgumentsException("Score cannot be negative");
        }
        onGoingGameScore.updateScore(homeTeamScore, awayTeamScore);
    }

    @Override
    public List<GameScoreDto> getGamesSummary() {
        return gameScores.stream()
                .map(GameScoreDto::of)
                .toList();
    }
}
