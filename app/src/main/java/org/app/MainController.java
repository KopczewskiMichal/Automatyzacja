package org.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final GameRepository gameRepository;
    private final AchievementsRepository achievementsRepository;

    public MainController(GameRepository gameRepository, AchievementsRepository achievementsRepository) {
        this.gameRepository = gameRepository;
        this.achievementsRepository = achievementsRepository;
    }

    @PostMapping("/games")
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        System.out.println(game.toJson());
        gameRepository.save(game);
        return ResponseEntity.ok("Game created successfully");
    }

    @PostMapping("/games/{id}/achievements")
    public ResponseEntity<String> createAchievement(@PathVariable("id") int game_id, @RequestBody Achievement achievement) {
        achievement.setGame_id(game_id);
        // Tymczasowa logika do sprawdzenia achievementu
        System.out.println(achievement.toJson());
        achievementsRepository.save(achievement);
        return ResponseEntity.ok("Achievement created successfully");
    }
}
