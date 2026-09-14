package dev.omercanbasboga.hollowmereliveops.repository;

import dev.omercanbasboga.hollowmereliveops.model.LevelConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Nothing fancy, this is a toy live-ops knob, not a real config store.
 * A ConcurrentHashMap is plenty and means there's no DB to spin up for a demo.
 */
@Repository
public class InMemoryLevelConfigRepository implements LevelConfigRepository {

    private final Map<String, LevelConfig> store = new ConcurrentHashMap<>();

    @Override
    public Optional<LevelConfig> findById(String levelId) {
        return Optional.ofNullable(store.get(levelId));
    }

    @Override
    public List<LevelConfig> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public LevelConfig save(LevelConfig config) {
        store.put(config.levelId(), config);
        return config;
    }
}
