package com.app.aftas.repositories;


import com.app.aftas.models.Ranking;
import com.app.aftas.models.RankingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository  extends JpaRepository<Ranking, RankingId> {

    Ranking findByMemberIdAndCompetitionId(Long memberId, Long competitionId);

}
