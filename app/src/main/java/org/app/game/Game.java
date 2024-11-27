package org.app.game;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
@ToString(includeFieldNames = true)
@Builder
public class Game implements Serializable {
    private int id;
    private String tittle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private String category;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during deserialization", e);
        }
    }

//    public static Game fromJson(String json) {
//        try {
//            return objectMapper.readValue(json, Game.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Błąd podczas deserializacji z JSON", e);
//        }
//    }
}
