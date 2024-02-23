package com.app.aftas.controllers;

import com.app.aftas.handlers.response.ResponseMessage;
import com.app.aftas.models.Level;
import com.app.aftas.services.LevelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/levels")
@PreAuthorize("hasRole('ROLE_MANAGER')")

public class LevelController {
    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
//    @PreAuthorize("hasAnyAuthority('VIEW_LEVELS')")
    @GetMapping
    public ResponseEntity getAllLevels() {
        List<Level> levels = levelService.getAllLevels();
        if(levels.isEmpty()) {
            return ResponseMessage.notFound("Levels not found");
        }else {
            return ResponseMessage.ok(levels, "Success");
        }
    }
    @PreAuthorize("hasAnyAuthority('VIEW_ONE_LEVEL')")
    @GetMapping("/{id}")
    public ResponseEntity getLevelById(@PathVariable Long id) {
        Level level = levelService.getLevelById(id);
        if(level == null) {
            return ResponseMessage.notFound("Level not found");
        }else {
            return ResponseMessage.ok(level, "Success");
        }
    }

    @PreAuthorize("hasAnyAuthority('CREATE_LEVEL')")
    @PostMapping
    public ResponseEntity addLevel(@Valid @RequestBody Level level) {
        try {
            Level levelCreated = levelService.addLevel(level);
            if (levelCreated == null) {
                return ResponseMessage.badRequest("Level not created");
            } else {
                return ResponseMessage.created(levelCreated, "Level created successfully");
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseMessage.badRequest("Internal server error");
        }
    }
    @PreAuthorize("hasAnyAuthority('UPDATE_LEVEL')")
    @PutMapping("/{id}")
    public ResponseEntity updateLevel(@Valid @RequestBody Level level, @PathVariable Long id) {
        Level level1 = levelService.updateLevel(level, id);
        if(level1 == null) {
            return ResponseMessage.badRequest("Level not updated");
        }else {
            return ResponseMessage.created(level1, "Level updated successfully");
        }
    }
    @PreAuthorize("hasAnyAuthority('DELETE_LEVEL')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteLevel(@PathVariable Long id) {
        Level level = levelService.getLevelById(id);
        if(level == null) {
            return ResponseMessage.notFound("Level not found");
        }else {
            levelService.deleteLevel(id);
            return ResponseMessage.ok(null,"Level deleted successfully");
        }
    }

}
