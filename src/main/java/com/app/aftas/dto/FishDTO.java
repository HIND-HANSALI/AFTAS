package com.app.aftas.dto;

import com.app.aftas.models.Fish;
import com.app.aftas.models.Level;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record FishDTO(
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")

    @Column(unique = true)
    String name,

    @NotNull(message = "Average weight is required")
    @Range(min = 0, max = 1000, message = "Weight must be between 0 and 1000")
    @Positive(message = "Weight must be greater than 0")
    double averageWeight,

    Long level_id
){
    public Fish toFish() {
        Fish.FishBuilder fishBuilder =  new Fish().builder()
                .name(name)
                .averageWeight(averageWeight);
        if(level_id != null) {
            fishBuilder.level(Level.builder().id(level_id).build());
        }
        return fishBuilder.build();
    }

}
