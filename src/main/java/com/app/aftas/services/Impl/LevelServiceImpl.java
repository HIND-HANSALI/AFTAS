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

//    @Override
//    public Level addLevel(Level level) {
//        return levelRepository.save(level);
//    }

    @Override
    public Level addLevel(Level level) {
        Level levels = levelRepository.findAll().stream().max((l1, l2) -> l1.getPoints() > l2.getPoints() ? 1 : -1).orElse(null);
        if(levels != null) {
            if(level.getPoints() <= levels.getPoints()) {
                throw new OperationException("Point must be greater than " + levels.getPoints());
            }
        }
        return levelRepository.save(level);
    }

    @Override
    public Level updateLevel(Level level, Long id) {
        Level existingLevel = getLevelById(id);
        existingLevel.setDescription(level.getDescription());
        List<Level> levels1 = levelRepository.findAll();
        int index = id.intValue()-1;
        // check if point is > than last level and < than next level
        if(index==0){
            if(level.getPoints() >= levels1.get((index+1)).getPoints()) {
                throw new OperationException("Point must be less than " + levels1.get((index+1)).getPoints());
            }
        }else if(index==levels1.size()-1){
            if(level.getPoints() <= levels1.get((index-1)).getPoints()) {
                throw new OperationException("Point must be greater than " + levels1.get((index-1)).getPoints());
            }
        }else{
            if(level.getPoints() >= levels1.get((index+1)).getPoints()) {
                throw new OperationException("Point must be less than " + levels1.get((index+1)).getPoints());
            }
            if(level.getPoints() <= levels1.get((index-1)).getPoints()) {
                throw new OperationException("Point must be greater than " + levels1.get((index-1)).getPoints());
            }
        }
        existingLevel.setPoints(level.getPoints());
        return levelRepository.save(existingLevel);
    }

    @Override
    public void deleteLevel(Long id) {levelRepository.deleteById(id);}
}
