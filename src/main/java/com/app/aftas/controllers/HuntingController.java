package com.app.aftas.controllers;

import com.app.aftas.dto.FishDTO;
import com.app.aftas.dto.HuntingDto;
import com.app.aftas.dto.HuntingUpdateDTO;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Competition;
import com.app.aftas.models.Fish;
import com.app.aftas.models.Hunting;
import com.app.aftas.services.HuntingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/huntings")
public class HuntingController {
    private HuntingService huntingService;


    public HuntingController(HuntingService huntingService) {
        this.huntingService = huntingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getHuntingById(@PathVariable Long id) {
        return ResponseMessage.ok(huntingService.getHuntingById(id), "Success");
    }
    @GetMapping
    public ResponseEntity getAllHuntings() {
        List<Hunting> huntings = huntingService.getAllHuntings();
        if(huntings.isEmpty()) {
            return ResponseMessage.notFound("Hunting not found");
        }else {
            return ResponseMessage.ok(huntings, "Success");
        }
    }
    @PostMapping
    public ResponseEntity addHuntingResult(@Valid @RequestBody HuntingDto hunting) {
        Hunting huntingC = huntingService.addHuntingResult(hunting.toHunting());
        if(huntingC == null) {
            return ResponseMessage.badRequest("Hunting not created");
        }else {
            return ResponseMessage.created(huntingC, "Hunting created successfully");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateHunting(@RequestBody HuntingUpdateDTO huntingUpdateDTO, @PathVariable Long id) {
        Hunting hunting = huntingService.updateHunting(huntingUpdateDTO.toHunting(), id);
        if(hunting == null) {
            return ResponseMessage.badRequest("Hunting not updated");
        }else {
            return ResponseMessage.created(hunting, "Hunting updated successfully");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteHunting(@PathVariable Long id) {
        huntingService.deleteHunting(id);
        return ResponseMessage.ok(null,"Hunting deleted successfully");
    }
}
