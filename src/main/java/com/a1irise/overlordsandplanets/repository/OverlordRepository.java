package com.a1irise.overlordsandplanets.repository;

import com.a1irise.overlordsandplanets.entity.Overlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OverlordRepository extends JpaRepository<Overlord, Long> {

    Optional<Overlord> findByName(String name);

    @Query("SELECT o FROM Overlord o WHERE NOT EXISTS (SELECT p FROM Planet p WHERE p.overlord = o)")
    List<Overlord> findAllSlackers();

    List<Overlord> findTop10ByOrderByAgeAsc();
}
