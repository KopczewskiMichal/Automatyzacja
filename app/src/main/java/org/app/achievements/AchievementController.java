package org.app.achievements;

import org.app.games.Game;
import org.app.games.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
