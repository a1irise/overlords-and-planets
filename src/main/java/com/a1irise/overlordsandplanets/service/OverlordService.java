package com.a1irise.overlordsandplanets.service;

import com.a1irise.overlordsandplanets.dto.Mapper;
import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;
import com.a1irise.overlordsandplanets.exception.OverlordAlreadyExistsException;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OverlordService {

    private OverlordRepository overlordRepository;
    private PlanetRepository planetRepository;

    @Autowired
    public OverlordService(OverlordRepository overlordRepository,
                           PlanetRepository planetRepository) {
        this.overlordRepository = overlordRepository;
        this.planetRepository = planetRepository;
    }

    public void addOverlord(OverlordDto overlordDto) {
        if (overlordRepository.findByName(overlordDto.getName()) != null) {
            throw new OverlordAlreadyExistsException("Overlord with name \"" + overlordDto.getName() + "\" already exists.");
        }

        Overlord overlord = Mapper.toOverlord(overlordDto);
        overlordRepository.save(overlord);
    }

    public List<OverlordDto> findSlackers() {
        List<Overlord> all = overlordRepository.findAll();
        List<Overlord> busy = planetRepository.findAll()
                .stream()
                .map(Planet::getOverlord)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<OverlordDto> slackers = new ArrayList<>();
        for (Overlord overlord: all) {
            if (!busy.contains(overlord)) {
                slackers.add(Mapper.toOverlordDto(overlord));
            }
        }

        return slackers;
    }

    public List<OverlordDto> findTopTenYoungest() {
        PageRequest topTen = PageRequest.of(0, 10, Sort.Direction.ASC, "age");
        return overlordRepository.findWithPageable(topTen)
                .stream()
                .map(Mapper::toOverlordDto)
                .toList();
    }
}
