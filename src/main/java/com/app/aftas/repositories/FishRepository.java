package com.app.aftas.repositories;

import com.app.aftas.models.Fish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FishRepository extends JpaRepository<Fish, Long> {
    Fish findByName(String name);

}
