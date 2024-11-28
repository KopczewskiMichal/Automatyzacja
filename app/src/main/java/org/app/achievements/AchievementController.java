package org.app.achievements;

import org.app.games.Game;
import org.app.games.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AchievementController {
    private final AchievementsRepository achievementsRepository;
    private final GameRepository gameRepository;

    public AchievementController(GameRepository gameRepository, AchievementsRepository achievementsRepository) {
        this.achievementsRepository = achievementsRepository;
        this.gameRepository = gameRepository;

    }


    @PostMapping("/games/{id}/achievements")
    public ResponseEntity<String> createAchievement(@PathVariable("id") int game_id, @RequestBody Achievement achievement) {
        Optional<Game> gameOptional = gameRepository.findById(game_id);
        if (gameOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
        Game game = gameOptional.get();
        achievement.setGame(game);
        achievementsRepository.save(achievement);
        return ResponseEntity.ok("Achievement created successfully");
    }

    @GetMapping("/games/{id}/achievements")
    public ResponseEntity<?> getAllAchievementsByGame(@PathVariable("id") int game_id) {
        Optional<Game> gameOptional = gameRepository.findById(game_id);
        if (gameOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
        Game game = gameOptional.get();
        List<Achievement> achievements = achievementsRepository.findAllByGame(game);
        return ResponseEntity.ok(achievements);
    }

    @DeleteMapping("/games/{game_id}/{achievement_id}")
    public ResponseEntity<String> deleteAchievement(@PathVariable("game_id") int game_id, @PathVariable("achievement_id") int achievement_id) {
        Optional<Game> gameOptional = gameRepository.findById(game_id);
        if (gameOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
        Game game = gameOptional.get();
        Optional<Achievement> achievementOptional = achievementsRepository.findById(achievement_id);
        if (achievementOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Achievement not found");
        }
        Achievement achievement = achievementOptional.get();
        if (!achievement.getGame().equals(game)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Achievement does not belong to the given game");
        }
        achievementsRepository.delete(achievement);
        return ResponseEntity.ok("Achievement deleted successfully");
    }

}
