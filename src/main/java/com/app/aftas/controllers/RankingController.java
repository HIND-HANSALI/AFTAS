package com.app.aftas.controllers;

import com.app.aftas.dto.UpdateRankingDto;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Fish;
import com.app.aftas.models.Ranking;
import com.app.aftas.models.RankingId;
import com.app.aftas.services.RankingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {
    private RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    @GetMapping
    public ResponseEntity getAllRankings() {

        List<Ranking> rankings = rankingService.getAllRankings();
        if(rankings.isEmpty()) {
            return ResponseMessage.notFound("Ranking not found");
        }else {
            return ResponseMessage.ok(rankings, "Success");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getRankingById(@PathVariable RankingId id) {
        Ranking ranking = rankingService.getRankingById(id);
        return ResponseMessage.ok(ranking,"Success");
    }

    @GetMapping("/{competitionId}/{memberId}")
    public ResponseEntity getRankingsByMemberIdAndCompetitionId(@PathVariable Long competitionId, @PathVariable Long memberId) {
        Ranking ranking = rankingService.getRankingsByMemberIdAndCompetitionId(competitionId, memberId);
        return ResponseMessage.ok(ranking,"Success");
    }

//    @GetMapping("/competitions/{code}/podium")
//    public ResponseEntity findPodiumByCompetitionCode(@PathVariable String code) {
//        List <Ranking> rankings=rankingService.findPodiumByCompetitionCode(code);
//        return ResponseMessage.ok(rankings,"Success");
//    }
    @GetMapping("/competitions/{id}/podium")
    public ResponseEntity findPodiumByCompetitionId(@PathVariable Long id) {
        List <Ranking> rankings=rankingService.findPodiumByCompetitionId(id);
        return ResponseMessage.ok(rankings,"Success");
    }

    @PutMapping("/updateRank/{code}")
    public ResponseEntity updateRankOfMemberInCompetition(@PathVariable String code){
        List <Ranking> rankings=rankingService.updateRankOfMemberInCompetition(code);
        return ResponseMessage.ok(rankings,"Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRanking(@Valid @RequestBody UpdateRankingDto updateRankingDto, @PathVariable RankingId id) {
        Ranking ranking1 = rankingService.updateRanking(updateRankingDto.toRanking(), id);
        return ResponseMessage.ok(ranking1,"Success");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteRanking(@PathVariable RankingId id) {
        rankingService.deleteRanking(id);
        return ResponseMessage.ok(null,"Ranking deleted successfully");
    }
}
