package org.app.games;

import org.app.achievements.AchievementsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository, AchievementsRepository achievementsRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping("/games")
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        System.out.println(game.toJson());
        gameRepository.save(game);
        return ResponseEntity.ok("Game created successfully");
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable int id) {
        return gameRepository.findById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable int id) {
        gameRepository.deleteById(id);
        return ResponseEntity.ok("Game deleted successfully");
    }

}
