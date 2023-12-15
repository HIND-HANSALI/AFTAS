package com.app.aftas.dto;

import com.app.aftas.models.Competition;
import com.app.aftas.models.Member;
import com.app.aftas.models.Ranking;

public record RankingDTO(
        Long competitionId,
        Long memberId
) {
    public Ranking toRanking() {
        return Ranking.builder()
                .competition(Competition.builder().id(competitionId).build())
                .member(Member.builder().id(memberId).build())
                .build();
    }
}

