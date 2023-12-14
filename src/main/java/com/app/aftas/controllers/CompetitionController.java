package com.app.aftas.controllers;

import com.app.aftas.dto.FishDTO;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Competition;
import com.app.aftas.models.Fish;
import com.app.aftas.services.CompetitionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    private CompetitionService competitionService;


    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getCompetitionById(@PathVariable Long id) {
        Competition competition = competitionService.getCompetitionById(id);
        if(competition == null) {
            return ResponseMessage.notFound("Competition not found");
        }else {
            return ResponseMessage.ok(competition, "Success");
        }
    }

    @GetMapping
    public ResponseEntity getAllCompetitions() {
        List<Competition> competitions = competitionService.getAllCompetitions();
        if(competitions.isEmpty()) {
            return ResponseMessage.notFound("Competition not found");
        }else {
            return ResponseMessage.ok(competitions, "Success");
        }
    }

    @PostMapping
    public ResponseEntity addCompetition( @RequestBody Competition competition) {
        Competition competitionC = competitionService.addCompetition(competition);
        if(competitionC == null) {
            return ResponseMessage.badRequest("Competition not created");
        }else {
            return ResponseMessage.created(competitionC, "Competition created successfully");
        }
    }
    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
    }
}

