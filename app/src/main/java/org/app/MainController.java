package org.app;

import org.app.game.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping("/games")
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        // Tymczasowa logika do sprawdzenia klasy game
        System.out.println(game.toString());
        System.out.println(game.toJson());


        return ResponseEntity.ok("Game created successfully");
    }
}
