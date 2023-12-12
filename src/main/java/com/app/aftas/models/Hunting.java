package com.app.aftas.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hunting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numberOfFish;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Fish fish;

    @ManyToOne
    private Competition competition;
}
