package scoreboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class Scoreboard {
    private final List<Match> matches;

    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    public void startMatch(String homeTeam, String awayTeam) {
        Optional<Match> match = findMatch( homeTeam, awayTeam );
        if (match.isPresent()) {
            throw new IllegalArgumentException("Match between these teams is already in progress");
        }

        matches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match match = findMatch( homeTeam, awayTeam )
                .orElseThrow( () -> new IllegalArgumentException("Match not found") );

        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        if (homeScore < match.getHomeScore() || awayScore < match.getAwayScore()) {
            throw new IllegalArgumentException("Scores cannot be decreased");
        }

        match.updateScore(homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Optional<Match> match = findMatch( homeTeam, awayTeam );
        if (match.isPresent()) {
            matches.remove( match.get() );
            return;
        }
        throw new IllegalArgumentException("Match not found");
    }

    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalScore)
                        .thenComparing(Match::getStartTime)
                        .reversed())
                .toList();
    }

    private Optional<Match> findMatch(String homeTeam, String awayTeam) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
                .findFirst();
    }
}
