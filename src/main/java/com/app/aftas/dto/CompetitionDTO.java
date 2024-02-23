package com.app.aftas.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record CompetitionDTO(
        Long id,
        String code,
        @NotNull(message = "Name cannot be null")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @NotNull(message = "Start time cannot be null")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,
        @NotNull(message = "Start time cannot be null")
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,
        @NotNull(message = "Location cannot be null")
        @Size(min = 2, max = 50, message = "Location must be between 2 and 50 characters")
        String location,
        @NotNull(message = "Amount cannot be null")
        @Min(value = 0, message = "Amount must be greater than 0")
        int amount,
        @NotNull(message = "Total member cannot be null")
        @Min(value = 0, message = "Total member must be greater than 0")
        int totalMember
) {
}
