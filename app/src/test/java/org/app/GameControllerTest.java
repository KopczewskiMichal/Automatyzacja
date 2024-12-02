package org.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.app.achievements.AchievementsRepository;
import org.app.games.Game;
import org.app.games.GameController;
import org.app.games.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

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
    void testCreateGame() throws Exception {
        Game game = new Game();
        game.setId(1);
        game.setTitle("GTA");
        game.setReleaseDate(java.sql.Date.valueOf("2022-05-15"));
        game.setCategory("Action");
        ObjectMapper objectMapper = new ObjectMapper();
        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isOk());

        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void testDeleteGame() throws Exception {
        int id = 1;

        doNothing().when(gameRepository).deleteById(id);
        mockMvc.perform(delete("/games/{id}", id))
                .andExpect(status().isOk());
        verify(gameRepository, times(1)).deleteById(id);
    }
}
