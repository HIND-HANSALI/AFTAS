package com.app.aftas.services;
import com.app.aftas.dto.RankingResponseDTO;
import com.app.aftas.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RankingService {


    List<RankingResponseDTO> getAllRankings();
    List<Ranking> findPodiumByCompetitionCode(String code);
    public List<RankingResponseDTO> findPodiumByCompetitionId(Long id);
    //    void updateRankingScoreAndRank(Member member, Competition competition, Fish fish);
    Ranking getRankingById(RankingId id);
    List<Ranking> updateRankOfMemberInCompetition(String competitionCode);

    Ranking addRanking(Ranking ranking);
    Ranking updateRanking(Ranking ranking, RankingId id);
    void deleteRanking(RankingId id);

    Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId);
}
