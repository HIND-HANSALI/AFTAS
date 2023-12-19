package com.app.aftas.services.Impl;

import com.app.aftas.handlers.exception.ResourceNotFoundException;
import com.app.aftas.models.Fish;
import com.app.aftas.repositories.FishRepository;
import com.app.aftas.services.FishService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FishServiceImpl implements FishService {

    private FishRepository fishRepository;

    private LevelServiceImpl levelService;

    public FishServiceImpl(FishRepository fishRepository,LevelServiceImpl levelService) {
        this.fishRepository = fishRepository;
        this.levelService = levelService;
    }

    @Override
    public List<Fish> getAllFishes() {return fishRepository.findAll();}

    @Override
    public Fish getFishById(Long id) {
        return fishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fish id " + id + " not found"));
    }

    @Override
    public Fish addFish(Fish fish) {

        if(fishRepository.findByName(fish.getName()) != null) {
            throw new ResourceNotFoundException("Fish name " + fish.getName() + " already exist");
        }
        // Check if the Level object is not null
        if (fish.getLevel() == null) {
            throw new ResourceNotFoundException("Level object is null");
        }

        if(levelService.getLevelById(fish.getLevel().getId()) == null) {
            throw new ResourceNotFoundException("Level id " + fish.getLevel().getId() + " not found");
        }

        return fishRepository.save(fish);
    }

    @Override
    public Fish updateFish(Fish fish, Long id) {

        Fish existingFish = getFishById(id);
        existingFish.setName(fish.getName());
        existingFish.setAverageWeight(fish.getAverageWeight());

        if(levelService.getLevelById(fish.getLevel().getId()) == null) {
            throw new ResourceNotFoundException("Level id " + fish.getLevel().getId() + " not found");
        }
        existingFish.setLevel(fish.getLevel());
        return fishRepository.save(existingFish);
    }

    @Override
    public void deleteFish(Long id) { fishRepository.deleteById(id);}
}
