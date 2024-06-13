package com.sportradar;

import com.sportradar.entity.GameScore;
import com.sportradar.exception.InvalidArgumentsException;
import com.sportradar.exception.InvalidOperationException;
import com.sportradar.model.GameScoreDto;
import com.sportradar.persistance.GameScorePersistenceService;

import java.util.*;

public class FootballScoreBoard implements ScoreBoard {
    private final GameScorePersistenceService gameScorePersistenceService;
    private GameScore onGoingGameScore;

    public FootballScoreBoard(GameScorePersistenceService gameScorePersistenceService) {
        this.gameScorePersistenceService = gameScorePersistenceService;
    }

    @Override
    public void startGame(String homeTeamName, String awayTeamName) {
        if (Objects.nonNull(onGoingGameScore)) {
            throw new InvalidOperationException("Unfinished game exists");
        }
        if (Objects.isNull(homeTeamName) || homeTeamName.isEmpty()
                || Objects.isNull(awayTeamName) || awayTeamName.isEmpty()) {
            throw new InvalidArgumentsException("Team name is required");
        }
        onGoingGameScore = new GameScore(homeTeamName, awayTeamName);
    }

    @Override
    public void finishGame() {
        if (Objects.isNull(onGoingGameScore)) {
            throw new InvalidOperationException("Game was not found");
        }
        gameScorePersistenceService.save(onGoingGameScore);
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
        return gameScorePersistenceService.findAllOrderedByTotalScoreAndAddedDateDesc().stream()
                .map(GameScoreDto::of)
                .toList();
    }
}
