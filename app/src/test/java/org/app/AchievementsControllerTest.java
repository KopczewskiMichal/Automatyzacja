package org.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.app.achievements.Achievement;
import org.app.achievements.AchievementController;
import org.app.achievements.AchievementsRepository;
import org.app.games.Game;
import org.app.games.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AchievementController.class)
public class AchievementsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameRepository gameRepository;

    @MockitoBean
    private AchievementsRepository achievementsRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(gameRepository);
        Mockito.reset(achievementsRepository);
    }

    @Test
    void testCreateAchievement() throws Exception {
        Game game = new Game();
        game.setId(1);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        Achievement achievement = new Achievement();
        achievement.setAchievement_id(1);
        achievement.setGame(game);
        achievement.setAchievement_date(java.sql.Date.valueOf("2022-05-15"));
        achievement.setDescription("Gracz zabił smoka");
        ObjectMapper objectMapper = new ObjectMapper();
        String achievementJson = objectMapper.writeValueAsString(achievement);

        mockMvc.perform(post("/games/1/achievements")
                       .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                       .content(achievementJson))
                .andExpect(status().isOk());
        verify(achievementsRepository, times(1)).save(any(Achievement.class));
    }
}
