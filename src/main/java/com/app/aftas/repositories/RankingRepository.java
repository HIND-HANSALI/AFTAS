package com.app.aftas.repositories;


import com.app.aftas.models.Competition;
import com.app.aftas.models.Ranking;
import com.app.aftas.models.RankingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingRepository  extends JpaRepository<Ranking, RankingId> {

    Ranking findByMemberIdAndCompetitionId(Long memberId, Long competitionId);
//    Optional<Ranking> findByCompetitionCodeAndMemberNum(String code, Integer num);
    List<Ranking> findAllByCompetitionCode(String competitionCode);
    List<Ranking> findByCompetitionIdOrderByScoreDesc(Long id);
    List<Ranking> findTop3ByCompetitionCodeOrderByScoreDesc(String code);

}
