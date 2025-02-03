package org.app.achievements;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.app.games.Game;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = true)
@Builder
@Entity
@Table(name = "achievements")
public class Achievement implements Serializable {
    public int getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(int achievement_id) {
        this.achievement_id = achievement_id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAchievement_date() {
        return achievement_date;
    }

    public void setAchievement_date(Date achievement_date) {
        this.achievement_date = achievement_date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int achievement_id;


    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(nullable = false)
    private String type;

    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date achievement_date;

    @Transient // Pole nie będzie zapisywane w bazie
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during deserialization", e);
        }
    }
}
