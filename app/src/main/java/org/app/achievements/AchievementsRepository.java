package org.app.achievements;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementsRepository extends JpaRepository<Achievement, Integer> {
}
