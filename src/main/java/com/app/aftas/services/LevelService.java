package com.app.aftas.services;

import com.app.aftas.models.Level;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface LevelService{

    List<Level> getAllLevels();
    Level getLevelById(Long id);
    Level addLevel(Level level);
    Level updateLevel(Level level, Long id);
    void deleteLevel(Long id);
}
