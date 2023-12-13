package com.app.aftas.services;

import com.app.aftas.models.Fish;

import java.util.List;

public interface FishService {

    Fish getFishById(Long id);
    List<Fish> getAllFishes();
    Fish addFish(Fish fish);
    Fish updateFish(Fish fish, Long id);
    void deleteFish(Long id);
}
