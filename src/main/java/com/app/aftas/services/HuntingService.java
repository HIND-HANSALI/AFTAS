package com.app.aftas.services;

import com.app.aftas.models.Hunting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HuntingService {

    Hunting getHuntingById(Long id);
    Hunting addHuntingResult(Hunting hunting);
    Hunting updateHunting(Hunting hunting, Long id);

    void deleteHunting(Long id);

}
