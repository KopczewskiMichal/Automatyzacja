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

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void testGetAllGames() throws Exception {
        Game game1 = new Game();
        game1.setId(1);
        game1.setTitle("GTA");
        game1.setReleaseDate(java.sql.Date.valueOf("2022-05-15"));
        game1.setCategory("Action");

        Game game2 = new Game();
        game2.setId(2);
        game2.setTitle("Red Dead Redemption II");
        game2.setReleaseDate(java.sql.Date.valueOf("2018-10-25"));
        game2.setCategory("Action-Adventure");

        List<Game> games = Arrays.asList(game1, game2);
        when(gameRepository.findAll()).thenReturn(games);

        mockMvc.perform(get("/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(game1.getId()))
                .andExpect(jsonPath("$[0].title").value(game1.getTitle()))
                .andExpect(jsonPath("$[0].releaseDate").value(game1.getReleaseDate().toString()))
                .andExpect(jsonPath("$[0].category").value(game1.getCategory()))
                .andExpect(jsonPath("$[1].id").value(game2.getId()))
                .andExpect(jsonPath("$[1].title").value(game2.getTitle()))
                .andExpect(jsonPath("$[1].releaseDate").value(game2.getReleaseDate().toString()))
                .andExpect(jsonPath("$[1].category").value(game2.getCategory()));

        verify(gameRepository, times(1)).findAll();
    }

//    @Test
//    void testGetGameById() throws Exception {
//        Game game = new Game();
//        game.setId(1);
//        game.setTitle("GTA");
//        game.setReleaseDate(java.sql.Date.valueOf("2022-05-15"));
//        game.setCategory("Action");
//
//        when(gameRepository.findById(1)).thenReturn(Optional.of(game));
//
//        mockMvc.perform(get("/games/{id}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.title").value("GTA"))
//                .andExpect(jsonPath("$.releaseDate").value("2022-05-15"))
//                .andExpect(jsonPath("$.category").value("Action"));
//    }

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
