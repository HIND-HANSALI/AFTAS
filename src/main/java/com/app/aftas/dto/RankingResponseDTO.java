package com.app.aftas.dto;

public record RankingResponseDTO(

        Long competitionId,
        Long memberId,
        int rank,
        int score
) {
}
