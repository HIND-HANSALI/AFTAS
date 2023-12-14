package com.app.aftas.services.Impl;

import com.app.aftas.handlers.exception.OperationException;
import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.Level;
import com.app.aftas.repositories.LevelRepository;
import com.app.aftas.services.LevelService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {this.levelRepository = levelRepository;}

    @Override
    public List<Level> getAllLevels() {return levelRepository.findAll();}

    @Override
    public Level getLevelById(Long id) {return levelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Level id " + id + " not found"));
    }

    @Override
    public Level addLevel(Level level) {
        return levelRepository.save(level);
    }

    @Override
    public Level updateLevel(Level level, Long id) {
        return null;
    }

    @Override
    public void deleteLevel(Long id) {levelRepository.deleteById(id);}
}
