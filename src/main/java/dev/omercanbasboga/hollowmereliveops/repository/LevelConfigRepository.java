package dev.omercanbasboga.hollowmereliveops.repository;

import dev.omercanbasboga.hollowmereliveops.model.LevelConfig;

import java.util.List;
import java.util.Optional;

public interface LevelConfigRepository {

    Optional<LevelConfig> findById(String levelId);

    List<LevelConfig> findAll();

    LevelConfig save(LevelConfig config);
}
