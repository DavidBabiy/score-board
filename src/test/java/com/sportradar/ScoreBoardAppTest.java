package com.sportradar;

import com.sportradar.exception.InvalidOperationException;
import com.sportradar.exception.InvalidArgumentsException;
import com.sportradar.model.GameScoreDto;
import com.sportradar.persistance.GameScorePersistenceService;
import com.sportradar.persistance.InMemoryGameScorePersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardAppTest {
    private ScoreBoard scoreBoard;
    private GameScorePersistenceService persistenceService;

    @BeforeEach
    public void setUp() {
        persistenceService = new InMemoryGameScorePersistenceService();
        scoreBoard = new FootballScoreBoard(persistenceService);
    }

    @Test
    @DisplayName("Case-1: Game should be started between teams")
    public void testScoreBoard_case1() {
        scoreBoard.startGame("Team 1", "Team 2");
    }

    @Test
    @DisplayName("Case-2: Game cannot be finished before started")
    public void testScoreBoard_case2() {
        assertThrows(InvalidOperationException.class, () -> scoreBoard.finishGame());
    }

    @Test
    @DisplayName("Case-3: Game cannot be started if previous game is not finished")
    public void testScoreBoard_case3() {
        scoreBoard.startGame("Team 1", "Team 2");
        assertThrows(InvalidOperationException.class, () -> scoreBoard.startGame("Team 3", "Team 4"));
    }

    @Test
    @DisplayName("Case-4: Game should be successfully finished after start")
    public void testScoreBoard_case4() {
        scoreBoard.startGame("Team 1", "Team 2");
        scoreBoard.finishGame();
    }

    @Test
    @DisplayName("Case-4: Game should be successfully started after finishing previous game between same team")
    public void testScoreBoard_case5() {
        scoreBoard.startGame("Team 1", "Team 2");
        scoreBoard.finishGame();
        scoreBoard.startGame("Team 1", "Team 2");
    }

    @Test
    @DisplayName("Case-6: Game cannot be started without valid teams' names")
    public void testScoreBoard_case6() {
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.startGame(null, "Team 4"));
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.startGame("Team 3", null));
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.startGame("", "Team 4"));
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.startGame("Team 3", ""));
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.startGame(null, ""));
    }


    @Test
    @DisplayName("Case-7: Score cannot be updated without ongoing game")
    public void testScoreBoard_case7() {
        assertThrows(InvalidOperationException.class, () -> scoreBoard.updateScore(1, 1));
    }

    @Test
    @DisplayName("Case-8: Score should be updated")
    public void testScoreBoard_case8() {
        scoreBoard.startGame("Team 1", "Team 2");
        scoreBoard.updateScore(1, 0);
        scoreBoard.updateScore(0, 0);
        scoreBoard.updateScore(0, 1);
        scoreBoard.updateScore(10, 3);
        scoreBoard.updateScore(4, 13);
        scoreBoard.updateScore(99, 99);
    }

    @Test
    @DisplayName("Case-9: Score cannot be negative")
    public void testScoreBoard_case9() {
        scoreBoard.startGame("Team 1", "Team 2");
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.updateScore(-1, 1));
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.updateScore(1, -1));
        assertThrows(InvalidArgumentsException.class, () -> scoreBoard.updateScore(-100, -100));
    }

    @Test
    @DisplayName("Case-10: Score summary must be empty in newly created board")
    public void testScoreBoard_case10() {
        scoreBoard.startGame("Team 1", "Team 2");
        assertTrue(scoreBoard.getGamesSummary().isEmpty());
    }

    @Test
    @DisplayName("Case-11: Score summary must be empty in newly created board")
    public void testScoreBoard_case11() {
        scoreBoard.startGame("Team 1", "Team 2");
        assertTrue(scoreBoard.getGamesSummary().isEmpty());
    }

    @Test
    @DisplayName("Case-12: Score summary must be sorted in DESC order by total score")
    public void testScoreBoard_case12() {
        scoreBoard.startGame("Team 1", "Team 2");
        scoreBoard.updateScore(2, 3);
        scoreBoard.finishGame();

        scoreBoard.startGame("Team 2", "Team 3");
        scoreBoard.updateScore(0, 3);
        scoreBoard.finishGame();

        scoreBoard.startGame("Team 1", "Team 3");
        scoreBoard.updateScore(4, 2);
        scoreBoard.finishGame();

        List<GameScoreDto> expected = List.of(
                new GameScoreDto("Team 1", 4, "Team 3", 2),
                new GameScoreDto("Team 1", 2, "Team 2", 3),
                new GameScoreDto("Team 2", 0, "Team 3", 3)
        );

        assertEquals(scoreBoard.getGamesSummary(), expected);
    }

    @Test
    @DisplayName("Case-13: Score summary must be sorted in DESC order by total score and recently added")
    public void testScoreBoard_case13() {
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.updateScore(0, 5);
        scoreBoard.finishGame();

        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.updateScore(10, 2);
        scoreBoard.finishGame();

        scoreBoard.startGame("Germany", "France");
        scoreBoard.updateScore(2, 2);
        scoreBoard.finishGame();

        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.updateScore(6, 6);
        scoreBoard.finishGame();

        scoreBoard.startGame("Argentina", "Australia");
        scoreBoard.updateScore(3, 1);
        scoreBoard.finishGame();

        List<GameScoreDto> expected = List.of(
                new GameScoreDto("Uruguay", 6, "Italy", 6),
                new GameScoreDto("Spain", 10, "Brazil", 2),
                new GameScoreDto("Mexico", 0, "Canada", 5),
                new GameScoreDto("Argentina", 3, "Australia", 1),
                new GameScoreDto("Germany", 2, "France", 2)
        );

        assertEquals(scoreBoard.getGamesSummary(), expected);
    }

    @Test
    @DisplayName("Case-14: Ongoing game should not appear in summary")
    public void testScoreBoard_case14() {
        scoreBoard.startGame("Mexico", "Poland");
        scoreBoard.updateScore(0, 5);
        scoreBoard.finishGame();

        scoreBoard.startGame("Norway", "Austria");
        scoreBoard.updateScore(2, 2);
        scoreBoard.finishGame();

        scoreBoard.startGame("Brazil", "Spain");
        scoreBoard.updateScore(7, 5);
        scoreBoard.finishGame();

        scoreBoard.startGame("Ukraine", "France");
        scoreBoard.updateScore(11, 2);
        scoreBoard.finishGame();

        scoreBoard.startGame("Netherlands", "Sweden");
        scoreBoard.updateScore(3, 1);


        List<GameScoreDto> expected = List.of(
                new GameScoreDto("Ukraine", 11, "France", 2),
                new GameScoreDto("Brazil", 7, "Spain", 5),
                new GameScoreDto("Mexico", 0, "Poland", 5),
                new GameScoreDto("Norway", 2, "Austria", 2)
        );

        assertEquals(scoreBoard.getGamesSummary(), expected);
    }
}
