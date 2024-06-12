package com.sportradar;

import com.sportradar.exception.GameNotFoundException;
import com.sportradar.exception.NegativeScoreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ScoreBoard.
 */
public class ScoreBoardAppTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    public void setUp() {
        // TODO: David: initialize scoreBoard with implementation
    }

    @Test
    @DisplayName("Case-1: Score cannot be updated without ongoing game")
    public void testScoreBoard_case1() {
        assertThrows(GameNotFoundException.class, () -> scoreBoard.updateScore(1, 1));
    }

    @Test
    @DisplayName("Case-2: Score should be updated")
    public void testScoreBoard_case2() {
        scoreBoard.startGame("Team 1", "Team 2");
        scoreBoard.updateScore(1, 0);
        scoreBoard.updateScore(0, 0);
        scoreBoard.updateScore(0, 1);
        scoreBoard.updateScore(10, 3);
        scoreBoard.updateScore(4, 13);
        scoreBoard.updateScore(99, 99);
    }

    @Test
    @DisplayName("Case-3: Score cannot be negative")
    public void testScoreBoard_case3() {
        scoreBoard.startGame("Team 1", "Team 2");
        assertThrows(NegativeScoreException.class, () -> scoreBoard.updateScore(-1, 1));
        assertThrows(NegativeScoreException.class, () -> scoreBoard.updateScore(1, -1));
        assertThrows(NegativeScoreException.class, () -> scoreBoard.updateScore(-100, -100));
    }

    @Test
    @DisplayName("Case-4: Game cannot be finished before started")
    public void testScoreBoard_case4() {
        assertThrows(GameNotFoundException.class, () -> scoreBoard.finishGame());
        scoreBoard.startGame("Team 1", "Team 2");
    }

}
