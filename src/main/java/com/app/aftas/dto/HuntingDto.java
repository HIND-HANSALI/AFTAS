package com.app.aftas.dto;

import com.app.aftas.models.Competition;
import com.app.aftas.models.Fish;
import com.app.aftas.models.Hunting;
import com.app.aftas.models.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HuntingDto(
        Long competitionId,
        Long memberId,
        Long fishId,

        int numberOfFish,
        @NotNull(message = "Weight is required")
        @Min(value = 1, message = "Weight must be greater than 0")
        double averageWeight
) {
    public Hunting toHunting() {
        return Hunting.builder()
                .numberOfFish(numberOfFish)
                .competition(Competition.builder().id(competitionId).build())
                .member(Member.builder().id(memberId).build())
                .fish(Fish.builder().id(fishId).averageWeight(averageWeight).build())
                .build();
    }
}
