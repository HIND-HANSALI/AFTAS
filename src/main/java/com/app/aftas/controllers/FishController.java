package com.app.aftas.controllers;

import com.app.aftas.dto.FishDTO;
import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Fish;
import com.app.aftas.services.FishService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/fishes")
@PreAuthorize("hasRole('JURY') || hasRole('ROLE_MANAGER')")
public class FishController {
    private FishService fishService;

    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping
    public ResponseEntity getAllFishes() {
        List<Fish> fishes = fishService.getAllFishes();
        if(fishes.isEmpty()) {
            return ResponseMessage.notFound("Fish not found");
        }else {
            return ResponseMessage.ok(fishes, "Success");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getFishById(@PathVariable Long id) {
        Fish fish = fishService.getFishById(id);
        if(fish == null) {
            return ResponseMessage.notFound("Fish not found");
        }else {
            return ResponseMessage.ok(fish, "Success");
        }
    }

    @PostMapping
    public ResponseEntity addFish(@Valid @RequestBody FishDTO fishDTO) {
        Fish fish = fishService.addFish(fishDTO.toFish());
        if(fish == null) {
            return ResponseMessage.badRequest("Fish not created");
        }else {
            return ResponseMessage.created(fish, "Fish created successfully");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFish(@Valid @RequestBody FishDTO fishDTO, @PathVariable Long id) {
        Fish fish = fishService.updateFish(fishDTO.toFish(), id);
        if(fish == null) {
            return ResponseMessage.badRequest("Fish not updated");
        }else {
            return ResponseMessage.created(fish, "Fish updated successfully");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteFish(@PathVariable Long id) {
        Fish fish = fishService.getFishById(id);
        if(fish == null) {
            return ResponseMessage.notFound("Fish not found");
        }else {
            fishService.deleteFish(id);
            return ResponseMessage.ok(null,"Fish deleted successfully");
        }
    }
}
