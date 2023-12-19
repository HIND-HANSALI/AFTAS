package com.app.aftas.services;

import com.app.aftas.models.Competition;
import com.app.aftas.models.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
@Service
public interface CompetitionService {

    Competition getCompetitionById(Long id);
    List<Competition> getAllCompetitions();
//    Page<Competition> findAll(Pageable pageable);
    Competition addCompetition(Competition competition);
    Competition updateCompetition(Competition competition, Long id);
    Ranking registerMemberForCompetition(Ranking ranking);
    void deleteCompetition(Long id);
}
