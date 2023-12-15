package com.app.aftas.dto;

import com.app.aftas.models.Hunting;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record HuntingUpdateDTO(
        @NotNull(message = "Number of fish is required")
        @Min(value = 1, message = "Number of fish must be greater than 0")
        int numberOfFish
) {
    public Hunting toHunting() {
        return Hunting.builder()
                .numberOfFish(numberOfFish)
                .build();
    }
}
