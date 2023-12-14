package com.app.aftas.repositories;

import com.app.aftas.models.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Competition findByDate(LocalDate date);
}
