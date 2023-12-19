package com.app.aftas.controllers;

import com.app.aftas.dto.FishDTO;
import com.app.aftas.dto.RankingDTO;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Competition;
import com.app.aftas.models.Fish;
import com.app.aftas.models.Ranking;
import com.app.aftas.services.CompetitionService;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
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


//    @GetMapping("getAll")
//    public ResponseEntity<List<Competition>> findAll(@ParameterObject Pageable pageable){
//
//        Page<Competition> competitionList = competitionService.findAll(pageable);
//
//        if (competitionList == null){
//            return ResponseMessage.notFound("Not found any competition");
//        }else {
//            return ResponseMessage.ok(competitionList,"The competitions has retrieved successfully");
//        }
//
//    }


    @PostMapping
    public ResponseEntity addCompetition( @RequestBody Competition competition) {
        Competition competitionC = competitionService.addCompetition(competition);
        if(competitionC == null) {
            return ResponseMessage.badRequest("Competition not created");
        }else {
            return ResponseMessage.created(competitionC, "Competition created successfully");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity updateCompetition(@RequestBody Competition competition, @PathVariable Long id) {
        System.out.println(competition);
        Competition competition1 = competitionService.updateCompetition(competition, id);
        if(competition1 == null) {
            return ResponseMessage.badRequest("Competition not updated");
        }else {
            return ResponseMessage.created(competition1, "Competition updated successfully");
        }
    }
    @PostMapping("/register-member")
    public ResponseEntity registerMemberForCompetition(@Valid @RequestBody RankingDTO registerMember) {
        Ranking ranking = competitionService.registerMemberForCompetition(registerMember.toRanking());
        return ResponseMessage.ok(ranking,"Member registered successfully");
    }
    @DeleteMapping("/{id}")
    public void deleteCompetition(@PathVariable Long id) {
        competitionService.deleteCompetition(id);
    }
}

