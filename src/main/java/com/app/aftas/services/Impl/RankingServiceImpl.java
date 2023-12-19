package com.app.aftas.services.Impl;

import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.*;
import com.app.aftas.repositories.CompetitionRepository;
import com.app.aftas.repositories.RankingRepository;
import com.app.aftas.services.RankingService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    public List<Ranking> updateRankOfMemberInCompetition(String competitionCode) {
        List<Ranking> rankings = rankingRepository.findAllByCompetitionCode(competitionCode);

        // check if there is any ranking for the competition
        if(rankings == null){
            throw new ResourceNotFoundException("Rankings for competition code " + competitionCode + " not found");
        }
        // sort the rankings by score and update the rank
        //rankings.sort((r1, r2) -> r2.getScore().compareTo(r1.getScore()));

        rankings.sort(Comparator.comparing(Ranking::getScore, Comparator.nullsLast(Comparator.reverseOrder())));


        // update the rank
        for(int i = 0; i < rankings.size(); i++){
            rankings.get(i).setRank(i + 1);
        }

        // save the rankings
        return rankingRepository.saveAll(rankings);
    }
    @Override
    public List<Ranking> findPodiumByCompetitionCode(String code) {
        List<Ranking> rankings = rankingRepository.findTop3ByCompetitionCodeOrderByScoreDesc(code);
        if (rankings.isEmpty()) {
            throw new ResourceNotFoundException("No podium found");
        } else {
            return rankings;
        }
    }


    @Override
    public List<Ranking> findPodiumByCompetitionId(Long id) {
        List<Ranking> allRankings = rankingRepository.findAllByCompetitionId(id).stream().sorted(Comparator.comparingInt(Ranking::getScore).reversed()).toList();
        allRankings.forEach(r -> r.setRank(allRankings.indexOf(r) + 1));
        rankingRepository.saveAll(allRankings);
        List<Ranking> rankings = rankingRepository.findTop3ByCompetitionIdOrderByRankAsc(id);
        if (rankings.isEmpty()) {
            throw new RuntimeException("No podium found");
        } else {
            return rankings;
        }
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
