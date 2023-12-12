package com.app.aftas.services;

import com.app.aftas.models.Competition;

import java.util.List;

public interface CompetitionService {

    Competition getCompetitionById(Long id);
    List<Competition> getAllCompetitions();
//    Competition addCompetition(Competition competition);
//    Competition updateCompetition(Competition competition, Long id);
    void deleteCompetition(Long id);
}
