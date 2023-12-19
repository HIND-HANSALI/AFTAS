package com.app.aftas.services.Impl;

import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.*;
import com.app.aftas.repositories.HuntingRepository;
import com.app.aftas.repositories.RankingRepository;
import com.app.aftas.services.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HuntingServiceImpl implements HuntingService {

    private HuntingRepository huntingRepository;
    private CompetitionService competitionService;
    private MemberService memberService;
    private FishService fishService;
    private RankingService rankingService;
    private RankingRepository rankingRepository;


    public HuntingServiceImpl(HuntingRepository huntingRepository, CompetitionService competitionService, MemberService memberService, FishService fishService, RankingService rankingService) {
        this.huntingRepository = huntingRepository;
        this.competitionService = competitionService;
        this.memberService = memberService;
        this.fishService = fishService;
        this.rankingService = rankingService;
    }
    @Override
    public List<Hunting> getAllHuntings(){return huntingRepository.findAll();}

//    @Override
//    public Hunting addHunting(Hunting hunting) {
//        return huntingRepository.save(hunting);
//
//    }

    @Override
    public Hunting addHuntingResult(Hunting hunting) {
        Long competitionId = hunting.getCompetition().getId();
        Long memberId = hunting.getMember().getId();
        Long fishId = hunting.getFish().getId();
        // check if competition exist
        Competition competition = competitionService.getCompetitionById(competitionId);

        // check if member exist
        Member member = memberService.getMemberById(memberId);
        // check if fish exist
        Fish fish = fishService.getFishById(fishId);
        // check if Member has already participated in this competition
        rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        // check if fish has level
        if (fish.getLevel() == null) {
            throw new ResourceNotFoundException("Fish id " + fishId + " has no level");
        }
        // check weight of fish must be greater than average weight
        if (hunting.getFish().getAverageWeight() < fish.getAverageWeight()) {
            throw new ResourceNotFoundException("Weight of fish must be greater than average weight");
        }
        // check if fish has already been caught by this member in this competition if yes acquirement the number of fish caught
        Hunting existingHunting = huntingRepository.findByCompetitionIdAndMemberIdAndFishId(competitionId, memberId, fishId);


        Ranking ranking = rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        ranking.setScore(ranking.getScore() + fish.getLevel().getPoints()); //check it after



        rankingService.updateRanking(ranking,ranking.getId()); //9dima


        if(existingHunting != null) {
            existingHunting.setNumberOfFish(existingHunting.getNumberOfFish() + 1);
            return huntingRepository.save(existingHunting);
        } else {
            return huntingRepository.save(hunting);
        }
    }
    @Override
    public Hunting getHuntingById(Long id) {
        return huntingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hunting id " + id + " not found"));
    }

    @Override
    public Hunting updateHunting(Hunting hunting, Long id) {
        Hunting existingHunting = getHuntingById(id);
//        existingHunting.setFish(hunting.getFish());
//        existingHunting.setMember(hunting.getMember());
//        existingHunting.setCompetition(hunting.getCompetition());
        existingHunting.setNumberOfFish(hunting.getNumberOfFish());
        return huntingRepository.save(existingHunting);
    }

    @Override
    public void deleteHunting(Long id) {
        huntingRepository.deleteById(id);
    }



}
