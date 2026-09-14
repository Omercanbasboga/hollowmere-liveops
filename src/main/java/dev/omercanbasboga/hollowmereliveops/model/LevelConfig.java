package dev.omercanbasboga.hollowmereliveops.model;

/**
 * A tunable set of knobs for one level. Board size, move budget, how much
 * of a ward meter needs filling to seal a creature, and a difficulty knob
 * that scales spawn weighting on the client.
 */
public record LevelConfig(
        String levelId,
        int boardSize,
        int moveLimit,
        int wardTarget,
        double difficultyMultiplier
) {
}
