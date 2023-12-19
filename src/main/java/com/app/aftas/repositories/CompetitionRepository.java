package com.app.aftas.repositories;

import com.app.aftas.models.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
//    Page<Competition> findAll(Pageable pageable);
    Competition findByDate(LocalDate date);
}
