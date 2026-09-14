# hollowmere-liveops

The config service behind [Hollowmere](https://github.com/Omercanbasboga/hollowmere), a little match-3
game. Board size, move limit, how full a creature's ward meter needs to be before it's sealed, a
difficulty knob, that kind of thing. Change a number here, no redeploy of the game needed.

Not tied to Hollowmere's actual gameplay code in any way, it's just a config store the frontend polls.
If this service is down, slow to wake up, or you're just running the game with no backend at all, the
game falls back to hardcoded defaults and plays fine either way.

## Endpoints

- `GET /api/configs`: every level currently configured
- `GET /api/configs/{levelId}`: one level, 404 if it doesn't exist yet
- `PUT /api/configs/{levelId}`: create or update a level's config

```
PUT /api/configs/3
{
  "boardSize": 8,
  "moveLimit": 22,
  "wardTarget": 9,
  "difficultyMultiplier": 1.3
}
```

Bounds are enforced server-side (boardSize 5-12, moveLimit 5-100, wardTarget 3-30, difficultyMultiplier
0.5-3.0) so a typo in a PUT request can't push out something that breaks the board for everyone.

Levels `1` and `2` are seeded with sane defaults on startup.

## Running locally

```bash
./mvnw spring-boot:run
```

Runs on port 8080 by default, or whatever `PORT` is set to (Render sets this itself).

## Tests

```bash
./mvnw test
```

Covers the boundary values specifically, not just "a valid one works and an invalid one doesn't."
The interesting bugs in range checks live at the edges (5 and 12 for board size, not 50).
