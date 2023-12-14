package com.app.aftas.services.Impl;

import com.app.aftas.handlers.exception.OperationException;
import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.Competition;
import com.app.aftas.repositories.CompetitionRepository;
import com.app.aftas.services.CompetitionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    private final CompetitionRepository competitionRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
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
    public void deleteCompetition(Long id) {
        getCompetitionById(id);
        competitionRepository.deleteById(id);
    }
}
