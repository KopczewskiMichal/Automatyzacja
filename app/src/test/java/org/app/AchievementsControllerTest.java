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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    void testGetAllAchievements() throws Exception {
        Game game = new Game();
        game.setId(1);
        Mockito.when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        Achievement achievement1 = new Achievement();
        achievement1.setAchievement_id(1);
        achievement1.setGame(game);
        achievement1.setAchievement_date(java.sql.Date.valueOf("2022-05-15"));
        achievement1.setDescription("Gracz zabił smoka");

        Achievement achievement2 = new Achievement();
        achievement2.setAchievement_id(2);
        achievement2.setGame(game);
        achievement2.setAchievement_date(java.sql.Date.valueOf("2022-05-16"));
        achievement2.setDescription("Gracz wygrał kartę");

        List<Achievement> achievements = Arrays.asList(achievement1, achievement2);

        Mockito.when(achievementsRepository.findAllByGame(game)).thenReturn(achievements);

        mockMvc.perform(get("/games/1/achievements")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].achievement_id").value(1))
                .andExpect(jsonPath("$[0].description").value("Gracz zabił smoka"))
                .andExpect(jsonPath("$[0].achievement_date").value("15-05-2022"))
                .andExpect(jsonPath("$[1].achievement_id").value(2))
                .andExpect(jsonPath("$[1].description").value("Gracz wygrał kartę"))
                .andExpect(jsonPath("$[1].achievement_date").value("16-05-2022"));
        verify(achievementsRepository, times(1)).findAllByGame(game);
    }

    @Test
    void testDeleteAchievement() throws Exception {
        int gameId = 1;
        int achievementId = 1;

        Game game = new Game();
        game.setId(gameId);

        Achievement achievement = new Achievement();
        achievement.setAchievement_id(achievementId);
        achievement.setGame(game);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(achievementsRepository.findById(achievementId)).thenReturn(Optional.of(achievement));
        doNothing().when(achievementsRepository).delete(achievement);

        mockMvc.perform(delete("/games/1/1"))
                .andExpect(status().isOk());
        verify(achievementsRepository, times(1)).delete(achievement);
    }

}
