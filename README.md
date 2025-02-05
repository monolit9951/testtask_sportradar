# Live Football World Cup Scoreboard Library

## Overview

The **Live Football World Cup Scoreboard** library is designed to manage and display live scores for football matches in a World Cup setting. The library allows users to start matches, update scores, finish matches, and retrieve a summary of ongoing matches sorted by their total score and start time.

---

## Features

1. **Start a Match**  
   Add a new match to the scoreboard with an initial score of `0-0`.  
   Parameters:
    - Home Team
    - Away Team

   **Edge Case:** If a match between the same home and away teams is already in progress, the system will throw an exception.

2. **Update Score**  
   Update the score of an ongoing match.  
   Parameters:
    - Home Team Score (absolute value)
    - Away Team Score (absolute value)

   **Edge Cases:**
    - Scores cannot be negative.
    - Scores cannot be decreased from their current values.
    - If the match is not found, an exception is thrown.

3. **Finish Match**  
   Remove a match from the scoreboard, indicating that it has ended.

   **Edge Case:** If the match does not exist, an exception is thrown.

4. **Get Summary**  
   Retrieve a list of ongoing matches, sorted:
    - **First** by total score (descending).
    - **Second** by the most recently started match for ties in total score.

---

## Example Usage

### Matches Started in Order and Updated Scores:
1. `Mexico 0 - Canada 5`
2. `Spain 10 - Brazil 2`
3. `Germany 2 - France 2`
4. `Uruguay 6 - Italy 6`
5. `Argentina 3 - Australia 1`

### Summary Output:
1. `Uruguay 6 - Italy 6`
2. `Spain 10 - Brazil 2`
3. `Mexico 0 - Canada 5`
4. `Argentina 3 - Australia 1`
5. `Germany 2 - France 2`

---

## Installation

1. Clone the repository.
   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory.
   ```bash
   cd <project-directory>
   ```

3. Make sure Maven is installed.

4. Run the tests to ensure everything is working correctly.
   ```bash
   mvn test
   ```
