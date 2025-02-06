package org.app.achievements;

import org.app.games.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementsRepository extends JpaRepository<Achievement, Integer> {
    List<Achievement> findAllByGame(Game game);
}
