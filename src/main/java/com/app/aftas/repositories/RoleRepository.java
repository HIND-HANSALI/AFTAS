package com.app.aftas.repositories;

import com.app.aftas.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByIsDefaultTrue();


    Optional<Role> findByName(String name);
}
