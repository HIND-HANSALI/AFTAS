package com.app.aftas.repositories;

import com.app.aftas.models.Hunting;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HuntingRepository extends JpaRepository<Hunting, Long> {

    Hunting findByCompetitionIdAndMemberIdAndFishId(Long competitionId, Long memberId, Long fishId);
}
