package dev.omercanbasboga.hollowmereliveops.service;

public record UpsertConfigRequest(
        int boardSize,
        int moveLimit,
        int wardTarget,
        double difficultyMultiplier
) {
}
