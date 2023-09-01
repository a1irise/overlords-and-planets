package com.a1irise.overlordsandplanets.repository;

import com.a1irise.overlordsandplanets.entity.Overlord;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverlordRepository extends JpaRepository<Overlord, Long> {

//    @Query(value = "SELECT * FROM overlord ORDER BY age ASC TOP 10", nativeQuery = true)
//    List<Overlord> findTopTenYoungest();

    @Query("FROM Overlord")
    List<Overlord> findWithPageable(PageRequest pageRequest);

    Overlord findByName(String name);
}
