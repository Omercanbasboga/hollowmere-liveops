package dev.omercanbasboga.hollowmereliveops.config;

import dev.omercanbasboga.hollowmereliveops.model.LevelConfig;
import dev.omercanbasboga.hollowmereliveops.repository.LevelConfigRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds a couple of levels so the client has something to fetch on first
 * boot without anyone touching the API by hand.
 */
@Component
public class DefaultLevelSeeder implements ApplicationRunner {

    private final LevelConfigRepository repository;

    public DefaultLevelSeeder(LevelConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        repository.save(new LevelConfig("1", 7, 20, 8, 1.0));
        repository.save(new LevelConfig("2", 8, 18, 10, 1.2));
    }
}
