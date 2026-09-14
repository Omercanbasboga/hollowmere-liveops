package dev.omercanbasboga.hollowmereliveops.service;

import dev.omercanbasboga.hollowmereliveops.exception.ConfigNotFoundException;
import dev.omercanbasboga.hollowmereliveops.exception.ConfigValidationException;
import dev.omercanbasboga.hollowmereliveops.model.LevelConfig;
import dev.omercanbasboga.hollowmereliveops.repository.LevelConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The whole point of this thing is that someone can nudge a level's
 * difficulty without a redeploy. Keeping the bounds here (not just on the
 * client) means a bad value can't leak out to every player at once.
 */
@Service
public class LevelConfigService {

    private static final int MIN_BOARD_SIZE = 5;
    private static final int MAX_BOARD_SIZE = 12;
    private static final int MIN_MOVE_LIMIT = 5;
    private static final int MAX_MOVE_LIMIT = 100;
    private static final int MIN_WARD_TARGET = 3;
    private static final int MAX_WARD_TARGET = 30;
    private static final double MIN_DIFFICULTY = 0.5;
    private static final double MAX_DIFFICULTY = 3.0;

    private final LevelConfigRepository repository;

    public LevelConfigService(LevelConfigRepository repository) {
        this.repository = repository;
    }

    public LevelConfig getConfig(String levelId) {
        return repository.findById(levelId)
                .orElseThrow(() -> new ConfigNotFoundException(levelId));
    }

    public List<LevelConfig> getAllConfigs() {
        return repository.findAll();
    }

    public LevelConfig upsertConfig(String levelId, UpsertConfigRequest body) {
        validate(body);
        LevelConfig config = new LevelConfig(
                levelId,
                body.boardSize(),
                body.moveLimit(),
                body.wardTarget(),
                body.difficultyMultiplier()
        );
        return repository.save(config);
    }

    private void validate(UpsertConfigRequest req) {
        if (req.boardSize() < MIN_BOARD_SIZE || req.boardSize() > MAX_BOARD_SIZE) {
            throw new ConfigValidationException(
                    "boardSize must be between " + MIN_BOARD_SIZE + " and " + MAX_BOARD_SIZE);
        }
        if (req.moveLimit() < MIN_MOVE_LIMIT || req.moveLimit() > MAX_MOVE_LIMIT) {
            throw new ConfigValidationException(
                    "moveLimit must be between " + MIN_MOVE_LIMIT + " and " + MAX_MOVE_LIMIT);
        }
        if (req.wardTarget() < MIN_WARD_TARGET || req.wardTarget() > MAX_WARD_TARGET) {
            throw new ConfigValidationException(
                    "wardTarget must be between " + MIN_WARD_TARGET + " and " + MAX_WARD_TARGET);
        }
        if (req.difficultyMultiplier() < MIN_DIFFICULTY || req.difficultyMultiplier() > MAX_DIFFICULTY) {
            throw new ConfigValidationException(
                    "difficultyMultiplier must be between " + MIN_DIFFICULTY + " and " + MAX_DIFFICULTY);
        }
    }
}
