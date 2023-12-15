package com.app.aftas.dto;

import com.app.aftas.models.Ranking;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateRankingDto(
        @NotNull(message = "Score is required")
        @Min(value = 0, message = "Score must be greater than 0")
        int score

) {
    public Ranking toRanking() {
        return Ranking
                .builder()
                .score(score)
                .build();
    }
}

