package com.app.aftas.services.Impl;

import com.app.aftas.models.Ranking;
import com.app.aftas.models.RankingId;
import com.app.aftas.repositories.CompetitionRepository;
import com.app.aftas.repositories.RankingRepository;
import com.app.aftas.services.RankingService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RankingServiceImpl implements RankingService {

    private RankingRepository rankingRepository;
    private MemberServiceImpl memberService;
    private CompetitionRepository competitionRepository;


    public RankingServiceImpl(RankingRepository rankingRepository, MemberServiceImpl memberService,CompetitionRepository competitionRepository) {
        this.rankingRepository = rankingRepository;
        this.memberService = memberService;
        this.competitionRepository=competitionRepository;

    }

//    @Override
//    public Ranking getRankingById(Long id) {
//        return rankingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ranking id " + id + " not found"));
//    }
    @Override
    public Ranking getRankingsByMemberIdAndCompetitionId(Long competitionId, Long memberId) {
        // check if member exists
        memberService.getMemberById(memberId);
        // check if competition exists
        competitionRepository.findById(competitionId);
        Ranking ranking= rankingRepository.findByMemberIdAndCompetitionId(memberId, competitionId);
        if (ranking == null) {
            throw new RuntimeException("Member id " + memberId + " has not participated in competition id " + competitionId);
        }
        return ranking;
    }

    @Override
    public List<Ranking> getAllRankings() {
        return rankingRepository.findAll();
    }

    @Override
    public Ranking getRankingById(RankingId id) {
        return rankingRepository.findById(id).orElseThrow(() -> new RuntimeException("Ranking id " + id + " not found"));
    }

    @Override
    public Ranking addRanking(Ranking ranking) {
        return rankingRepository.save(ranking);
    }

    @Override
    public Ranking updateRanking(Ranking ranking, RankingId id) {
        Ranking existingRanking = getRankingById(id);
        existingRanking.setRank(ranking.getRank());
        existingRanking.setScore(ranking.getScore());
        return rankingRepository.save(existingRanking);
    }

    @Override
    public void deleteRanking(RankingId id) {
        getRankingById(id);
        rankingRepository.deleteById(id);
    }
}
