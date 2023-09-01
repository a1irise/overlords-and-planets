package com.a1irise.overlordsandplanets.service;

import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OverlordService {

    private OverlordRepository overlordRepository;
    private PlanetRepository planetRepository;

    @Autowired
    public OverlordService(OverlordRepository overlordRepository, PlanetRepository planetRepository) {
        this.overlordRepository = overlordRepository;
        this.planetRepository = planetRepository;
    }

    public void addOverlord(String name, long age) {
        Overlord overlord = new Overlord(name, age);
        overlordRepository.save(overlord);
    }

    public List<Overlord> findTopTenYoungest() {
        PageRequest topTen = PageRequest.of(0, 10, Sort.Direction.ASC, "age");
        return overlordRepository.findWithPageable(topTen);
    }

    public List<Overlord> findSlackers() {
        List<Long> all = overlordRepository.findAll().stream().map(Overlord::getId).toList();
        List<Long> busy = planetRepository.findAll().stream().filter(x -> x.getOverlord() != null)
                .map(x -> x.getOverlord().getId()).distinct().toList();
        List<Overlord> slackers = new ArrayList<>();
        for (Long id: all) {
            if (!busy.contains(id)) {
                slackers.add(overlordRepository.findById(id).get());
            }
        }
        return slackers;
    }
}
