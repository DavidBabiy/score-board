package com.sportradar.persistance;

import com.sportradar.entity.GameScore;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class InMemoryGameScorePersistenceService implements GameScorePersistenceService {
    private final Set<GameScore> gameScores = new TreeSet<>(
            Comparator.comparingInt(GameScore::getTotalScore)
                    .thenComparingInt(GameScore::getCreatedDate)
                    .reversed()
    );

    @Override
    public void save(GameScore gameScore) {
        gameScore.setCreatedDate(gameScores.size()); // For sake of simplified testing, instead of date, index is set to keep track which record was recently added
        gameScores.add(gameScore);
    }

    @Override
    public Set<GameScore> findAllOrderedByTotalScoreAndAddedDateDesc() {
        return gameScores;
    }
}
