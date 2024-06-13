package com.sportradar.factory;

import com.sportradar.FootballScoreBoard;
import com.sportradar.ScoreBoard;
import com.sportradar.persistance.InMemoryGameScorePersistenceService;

public class FootballScoreBoardFactory implements ScoreBoardFactory {
    @Override
    public ScoreBoard create() {
        return new FootballScoreBoard(new InMemoryGameScorePersistenceService());
    }
}
