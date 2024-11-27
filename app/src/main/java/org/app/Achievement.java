package org.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = true)
@Builder
@Entity
@Table(name = "achievements")
public class Achievement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int achievement_id;

    @Column(nullable = false)
    private int game_id;

    @Column(nullable = false)
    private String name;

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

// TODO auto spradzenie czy gameID jest w grach, to ma być klucz obcy