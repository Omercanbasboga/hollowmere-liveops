package dev.omercanbasboga.hollowmereliveops.service;

import dev.omercanbasboga.hollowmereliveops.exception.ConfigNotFoundException;
import dev.omercanbasboga.hollowmereliveops.exception.ConfigValidationException;
import dev.omercanbasboga.hollowmereliveops.model.LevelConfig;
import dev.omercanbasboga.hollowmereliveops.repository.InMemoryLevelConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LevelConfigServiceTest {

    private LevelConfigService service;

    @BeforeEach
    void setUp() {
        service = new LevelConfigService(new InMemoryLevelConfigRepository());
    }

    @Test
    void upsertThenGetReturnsWhatWasSaved() {
        service.upsertConfig("1", new UpsertConfigRequest(7, 20, 8, 1.0));

        LevelConfig saved = service.getConfig("1");

        assertThat(saved.boardSize()).isEqualTo(7);
        assertThat(saved.moveLimit()).isEqualTo(20);
        assertThat(saved.wardTarget()).isEqualTo(8);
        assertThat(saved.difficultyMultiplier()).isEqualTo(1.0);
    }

    @Test
    void upsertTwiceReplacesRatherThanDuplicates() {
        service.upsertConfig("1", new UpsertConfigRequest(7, 20, 8, 1.0));
        service.upsertConfig("1", new UpsertConfigRequest(9, 25, 10, 1.5));

        assertThat(service.getAllConfigs()).hasSize(1);
        assertThat(service.getConfig("1").boardSize()).isEqualTo(9);
    }

    @Test
    void unknownLevelThrowsNotFound() {
        assertThatThrownBy(() -> service.getConfig("does-not-exist"))
                .isInstanceOf(ConfigNotFoundException.class);
    }

    @Test
    void boardSizeBoundariesAreAccepted() {
        service.upsertConfig("min", new UpsertConfigRequest(5, 20, 8, 1.0));
        service.upsertConfig("max", new UpsertConfigRequest(12, 20, 8, 1.0));

        assertThat(service.getConfig("min").boardSize()).isEqualTo(5);
        assertThat(service.getConfig("max").boardSize()).isEqualTo(12);
    }

    @Test
    void boardSizeJustOutsideBoundariesIsRejected() {
        assertThatThrownBy(() -> service.upsertConfig("too-small", new UpsertConfigRequest(4, 20, 8, 1.0)))
                .isInstanceOf(ConfigValidationException.class);
        assertThatThrownBy(() -> service.upsertConfig("too-big", new UpsertConfigRequest(13, 20, 8, 1.0)))
                .isInstanceOf(ConfigValidationException.class);
    }

    @Test
    void moveLimitOutOfRangeIsRejected() {
        assertThatThrownBy(() -> service.upsertConfig("x", new UpsertConfigRequest(7, 4, 8, 1.0)))
                .isInstanceOf(ConfigValidationException.class);
        assertThatThrownBy(() -> service.upsertConfig("x", new UpsertConfigRequest(7, 101, 8, 1.0)))
                .isInstanceOf(ConfigValidationException.class);
    }

    @Test
    void wardTargetOutOfRangeIsRejected() {
        assertThatThrownBy(() -> service.upsertConfig("x", new UpsertConfigRequest(7, 20, 2, 1.0)))
                .isInstanceOf(ConfigValidationException.class);
        assertThatThrownBy(() -> service.upsertConfig("x", new UpsertConfigRequest(7, 20, 31, 1.0)))
                .isInstanceOf(ConfigValidationException.class);
    }

    @Test
    void difficultyMultiplierOutOfRangeIsRejected() {
        assertThatThrownBy(() -> service.upsertConfig("x", new UpsertConfigRequest(7, 20, 8, 0.4)))
                .isInstanceOf(ConfigValidationException.class);
        assertThatThrownBy(() -> service.upsertConfig("x", new UpsertConfigRequest(7, 20, 8, 3.1)))
                .isInstanceOf(ConfigValidationException.class);
    }
}
