package scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    public void setUp() {
        scoreboard = new Scoreboard();
    }


    @Test
    void startMatchAddsMatchSuccessfully() {
        scoreboard.startMatch("Team A", "Team B");

        List<Match> summary = scoreboard.getSummary();
        assertEquals(1, summary.size());

        assertEquals("Team A", summary.get(0).getHomeTeam());
        assertEquals(0, summary.get(0).getHomeScore());

        assertEquals("Team B", summary.get(0).getAwayTeam());
        assertEquals(0, summary.get(0).getAwayScore());
    }

    @Test
    void startMatchThrowsExceptionIfMatchAlreadyExists() {
        scoreboard.startMatch("Team A", "Team B");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startMatch("Team A", "Team B"));

        assertEquals("Match between these teams is already in progress", exception.getMessage());
    }

    @Test
    void updateScoreUpdatesScoresSuccessfully() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 3, 2);

        List<Match> summary = scoreboard.getSummary();
        assertEquals(3, summary.get(0).getHomeScore());
        assertEquals(2, summary.get(0).getAwayScore());
    }

    @Test
    void updateScoreThrowsExceptionIfMatchNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", 1, 0));

        assertEquals("Match not found", exception.getMessage());
    }

    @Test
    void updateScoreThrowsExceptionIfScoreIsNegative() {
        scoreboard.startMatch("Team A", "Team B");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", -1, 0));

        assertEquals("Scores cannot be negative", exception.getMessage());
    }

    @Test
    void updateScoreThrowsExceptionIfScoreDecreases() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.updateScore("Team A", "Team B", 3, 2);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.updateScore("Team A", "Team B", 2, 2));

        assertEquals("Scores cannot be decreased", exception.getMessage());
    }

    @Test
    void finishMatchRemovesMatchSuccessfully() {
        scoreboard.startMatch("Team A", "Team B");
        scoreboard.finishMatch("Team A", "Team B");

        List<Match> summary = scoreboard.getSummary();
        assertTrue(summary.isEmpty());
    }

    @Test
    void finishMatchThrowsExceptionIfMatchNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.finishMatch("Team A", "Team B"));

        assertEquals("Match not found", exception.getMessage());
    }

    @Test
    void getSummaryOrdersMatchesCorrectlyByScoreAndStartTime() throws InterruptedException {
        scoreboard.startMatch("Mexico", "Canada");
        Thread.sleep(5);
        scoreboard.startMatch("Spain", "Brazil");
        Thread.sleep(5);
        scoreboard.startMatch("Germany", "France");
        Thread.sleep(5);
        scoreboard.startMatch("Uruguay", "Italy");
        Thread.sleep(5);
        scoreboard.startMatch("Argentina", "Australia");

        scoreboard.updateScore("Mexico", "Canada", 0, 5);       // Total 5
        scoreboard.updateScore("Spain", "Brazil", 10, 2);       // Total 12
        scoreboard.updateScore("Germany", "France", 2, 2);      // Total 4
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);       // Total 12
        scoreboard.updateScore("Argentina", "Australia", 3, 1); // Total 4

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Italy", summary.get(0).getAwayTeam());

        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Brazil", summary.get(1).getAwayTeam());

        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Canada", summary.get(2).getAwayTeam());

        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Australia", summary.get(3).getAwayTeam());

        assertEquals("Germany", summary.get(4).getHomeTeam());
        assertEquals("France", summary.get(4).getAwayTeam());
    }
}
