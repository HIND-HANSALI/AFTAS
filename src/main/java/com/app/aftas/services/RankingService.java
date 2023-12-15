package com.app.aftas.services;
import com.app.aftas.models.Ranking;
import com.app.aftas.models.RankingId;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RankingService {


    List<Ranking> getAllRankings();
    Ranking getRankingById(RankingId id);

    Ranking addRanking(Ranking ranking);
    Ranking updateRanking(Ranking ranking, RankingId id);
    void deleteRanking(RankingId id);

    Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId);
}
