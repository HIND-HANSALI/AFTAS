package com.app.aftas.services.Impl;

import com.app.aftas.enums.IdentityDocumentType;
import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.Competition;
import com.app.aftas.models.Member;
import com.app.aftas.models.Ranking;
import com.app.aftas.repositories.CompetitionRepository;
import com.app.aftas.repositories.RankingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
class RankingServiceImplTest {
    @Mock
    private RankingRepository rankingRepository;

    @InjectMocks
    private RankingServiceImpl rankingServiceImpl;

    private Ranking ranking;
    private Competition competition;
    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        member = new Member().builder()
                .accessDate(LocalDate.now()).membershipNumber(123)
                .identityNumber("HA212").familyName("HIND").name("Hind").nationality("marocaine")
                .identityDocumentType(IdentityDocumentType.PASSPORT).id(1L).build();
        competition = new Competition().builder().id(1l)
                .amount(30).code("ma-21-12-23").location("marrakech")
                .startTime(LocalTime.now())
                .endTime(LocalTime.now()).date(LocalDate.now()).totalMember(20).build();
        ranking = new Ranking().builder().rank(1).competition(competition)
                .member(member).score(20).build();
        competition.setRankings(List.of(ranking));
        member.setRankings(List.of(ranking));
    }

    @Test
    void findPodiumByCompetitionCode_returnRankings() {
        String codeCompetition="ma-21-12-23";
        List<Ranking> rankings = List.of(ranking);
        when(rankingRepository.findTop3ByCompetitionCodeOrderByScoreDesc(codeCompetition))
                .thenReturn(rankings);
        assertEquals(rankings, rankingServiceImpl.findPodiumByCompetitionCode(codeCompetition));
    }
    @Test
    void findPodiumByCompetitionCode_returnRankingEmpty() {
        String codeCompetition="ma-21-12-23";
        when(rankingRepository.findTop3ByCompetitionCodeOrderByScoreDesc(codeCompetition))
                .thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class,
                ()->rankingServiceImpl.findPodiumByCompetitionCode(codeCompetition));
    }
}
   /* @Override
    public List<Ranking> findPodiumByCompetitionCode(String code) {
        List<Ranking> rankings = rankingRepository.findTop3ByCompetitionCodeOrderByScoreDesc(code);
        if (rankings.isEmpty()) {
            throw new ResourceNotFoundException("No podium found");
        } else {
            return rankings;
        }
    }*/