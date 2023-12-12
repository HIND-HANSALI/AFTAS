package com.app.aftas.models;
import jakarta.persistence.*;


import java.util.List;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int level;
    private String description;
    private int points;

    @OneToMany(mappedBy = "level")
    private List<Fish> fishes;
}
