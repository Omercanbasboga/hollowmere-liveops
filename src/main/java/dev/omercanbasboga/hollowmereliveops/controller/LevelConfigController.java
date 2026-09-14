package dev.omercanbasboga.hollowmereliveops.controller;

import dev.omercanbasboga.hollowmereliveops.model.LevelConfig;
import dev.omercanbasboga.hollowmereliveops.service.LevelConfigService;
import dev.omercanbasboga.hollowmereliveops.service.UpsertConfigRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configs")
@CrossOrigin
public class LevelConfigController {

    private final LevelConfigService service;

    public LevelConfigController(LevelConfigService service) {
        this.service = service;
    }

    @GetMapping
    public List<LevelConfig> getAll() {
        return service.getAllConfigs();
    }

    @GetMapping("/{levelId}")
    public LevelConfig getOne(@PathVariable String levelId) {
        return service.getConfig(levelId);
    }

    @PutMapping("/{levelId}")
    public LevelConfig upsert(@PathVariable String levelId, @RequestBody UpsertConfigRequest body) {
        return service.upsertConfig(levelId, body);
    }
}
