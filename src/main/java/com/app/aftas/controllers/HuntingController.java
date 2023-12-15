package com.app.aftas.controllers;

import com.app.aftas.dto.FishDTO;
import com.app.aftas.dto.HuntingDto;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Fish;
import com.app.aftas.models.Hunting;
import com.app.aftas.services.HuntingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/huntings")
public class HuntingController {
    private HuntingService huntingService;


    public HuntingController(HuntingService huntingService) {
        this.huntingService = huntingService;
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


}
