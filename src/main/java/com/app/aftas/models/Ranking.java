package com.app.aftas.models;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int rank;
    private int score;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Competition competition;
}
