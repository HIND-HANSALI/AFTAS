package com.app.aftas.services.Impl;

import com.app.aftas.handlers.exception.OperationException;
import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.Competition;
import com.app.aftas.models.Member;
import com.app.aftas.models.Ranking;
import com.app.aftas.models.RankingId;
import com.app.aftas.repositories.CompetitionRepository;
import com.app.aftas.services.CompetitionService;
import com.app.aftas.services.MemberService;
import com.app.aftas.services.RankingService;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service

public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final MemberService memberService;
    private final RankingService rankingService;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository, MemberService memberService, RankingService rankingService) {
        this.competitionRepository = competitionRepository;
        this.memberService = memberService;
        this.rankingService = rankingService;
    }

    @Override
    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Competition id " + id + " not found"));
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

//    @Override
//    public Page<Competition> findAll(Pageable pageable) {
//        Page<Competition> competitionList = competitionRepository.findAll(pageable);
//        return competitionList;
//    }

//    @Override
//    public Page<Competition> findAll(Pageable pageable) {
//        List<Competition> competitionDtoList = new ArrayList<>();
//        competitionRepository.findAll(pageable)
//                .forEach(competition -> competitionDtoList.add(competition));
//        return new PageImpl<>(competitionDtoList, pageable, competitionRepository.count());
//    }

//    @Override
//    public Competition addCompetition(Competition competition) {
//        return competitionRepository.save(competition);
//    }

    @Override
    public Competition addCompetition(Competition competition) {

        // Ensure only one competition per day.
        Competition competition1 = competitionRepository.findByDate(competition.getDate());
        if (competition1 != null) {
            throw new OperationException("There is already a competition on " + competition.getDate());
        }

        // Ensure the competition's start time is before the end time.
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }
        // Check if the competition date is before today.
        LocalDate currentDate = LocalDate.now();
        LocalDate competitionDate = competition.getDate();
        if (competitionDate.isBefore(currentDate)) {
            throw new OperationException("Competition date must be in the future. Current date: " + currentDate + ", Competition date: " + competitionDate);
        }

        // Generate a unique competition code in the pattern: ims-yy-MM-dd (location abbreviation - year-month-day).
        String code = generateCode(competition.getLocation(), competition.getDate());
        competition.setCode(code);

        return competitionRepository.save(competition);

    }

    public static String generateCode(String location, LocalDate date) {
        String locationCode = location.substring(0, 3).toLowerCase();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
        String formattedDate = date.format(dateFormatter);

        return locationCode + "-" + formattedDate;
    }

    @Override
    public Competition updateCompetition(Competition competition, Long id) {
        Competition existingCompetition = getCompetitionById(id);

        // Verify if the date has changed to check for existing competitions on the new date.
        System.out.println(competition.getDate());
        System.out.println(existingCompetition.getDate());
        if (!competition.getDate().equals(existingCompetition.getDate())) {
            Competition competition1 = competitionRepository.findByDate(competition.getDate());
            if (competition1 != null) {
                throw new OperationException("There is already a competition on " + competition.getDate());
            }
        }
        existingCompetition.setDate(competition.getDate());
        if (competition.getStartTime().isAfter(competition.getEndTime())) {
            throw new OperationException("Start time must be before end time");
        }
        existingCompetition.setStartTime(competition.getStartTime());
        existingCompetition.setEndTime(competition.getEndTime());
        existingCompetition.setLocation(competition.getLocation());
        if(competition.getLocation() != existingCompetition.getLocation() || competition.getDate() != existingCompetition.getDate()){
            String code = generateCode(competition.getLocation(), competition.getDate());
            existingCompetition.setCode(code);
        }

        existingCompetition.setAmount(competition.getAmount());
        return competitionRepository.save(existingCompetition);
    }


    @Override
    public Ranking registerMemberForCompetition(Ranking ranking1) {
        Long competitionId = ranking1.getCompetition().getId();
        Long memberId = ranking1.getMember().getId();
        // Ensure that the competition exists
        Competition competition = getCompetitionById(competitionId);
        if(competition == null){
            throw new ResourceNotFoundException("Competition id " + competitionId + " not found");
        }
        // Ensure that the Member exist
        Member member = memberService.getMemberById(memberId);
        if(member == null){
            throw new ResourceNotFoundException("Member id " + memberId + " not found");
        }
        // Verify that the member is not already registered for that competition
        if(competition.getRankings().stream().anyMatch(ranking -> ranking.getMember().getId().equals(memberId))){
            throw new OperationException("Member id " + memberId + " is already registered for the competition");
        }
        // Ensure the competition hasn't started and there's at least a 24-hour gap before the start time
        if(competition.getStartTime().isBefore(competition.getStartTime().minusHours(24))){
            throw new OperationException("Competition id " + competitionId + " is closed for registration");
        }

        // Set the values for the embedded ID
        ranking1.setId(RankingId.builder().competitionId(competitionId).memberId(memberId).build());

        // Register the member for the competition.
        return rankingService.addRanking(ranking1);
    }

    @Override
    public void deleteCompetition(Long id) {
        getCompetitionById(id);
        competitionRepository.deleteById(id);
    }
}
