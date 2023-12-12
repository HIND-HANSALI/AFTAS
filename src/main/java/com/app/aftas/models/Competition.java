package com.app.aftas.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String date;
    private String startTime;
    private String endTime;
    private int numberOfParticipants;
    private String location;
    private int amountOfFish;

    @OneToMany(mappedBy = "competition")
    private List<Ranking> rankings;
}
