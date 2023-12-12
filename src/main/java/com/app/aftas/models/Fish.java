package com.app.aftas.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fish{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double averageWeight;

    @OneToMany(mappedBy = "fish")
    private List<Hunting> huntings;

    @ManyToOne
    private Level level;
}
