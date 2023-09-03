package com.a1irise.overlordsandplanets.repository;

import com.a1irise.overlordsandplanets.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    Planet findByName(String name);

    boolean existsByName(String name);

    @Transactional
    void deleteByName(String name);
}
