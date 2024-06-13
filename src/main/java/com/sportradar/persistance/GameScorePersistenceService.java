package com.sportradar.persistance;

import com.sportradar.entity.GameScore;

import java.util.Set;

public interface GameScorePersistenceService {
    void save(GameScore gameScore);
    Set<GameScore> findAllOrderedByTotalScoreAndAddedDateDesc();
}
