package com.a1irise.overlordsandplanets.service;

import com.a1irise.overlordsandplanets.dto.Mapper;
import com.a1irise.overlordsandplanets.dto.PlanetDto;
import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;
import com.a1irise.overlordsandplanets.exception.OverlordNotFoundException;
import com.a1irise.overlordsandplanets.exception.PlanetAlreadyExistsException;
import com.a1irise.overlordsandplanets.exception.PlanetAlreadyHasOverlordException;
import com.a1irise.overlordsandplanets.exception.PlanetNotFoundException;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;
    private OverlordRepository overlordRepository;

    @Autowired
    public PlanetService(PlanetRepository planetRepository,
                         OverlordRepository overlordRepository) {
        this.planetRepository = planetRepository;
        this.overlordRepository = overlordRepository;
    }

    public PlanetDto addPlanet(PlanetDto planetDto) {
        if (planetRepository.findByName(planetDto.getName()).isPresent()) {
            throw new PlanetAlreadyExistsException("Planet already exists.");
        }

        return Mapper.toPlanetDto(planetRepository.save(Mapper.toPlanet(planetDto)));
    }

    public void deletePlanet(long id) {
        planetRepository.findById(id).ifPresent(planetRepository::delete);
    }

    public PlanetDto assignOverlord(long planetId, long overlordId) {
        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(()-> new PlanetNotFoundException("Planet not found."));
        Overlord overlord = overlordRepository.findById(overlordId)
                .orElseThrow(() -> new OverlordNotFoundException("Overlord not found."));

        if (planet.getOverlord() != null) {
            throw new PlanetAlreadyHasOverlordException("Planet already has an overlord.");
        }

        planet.setOverlord(overlord);

        return Mapper.toPlanetDto(planetRepository.save(planet));
    }

    public PlanetDto getById(long id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(()-> new PlanetNotFoundException("Planet not found."));

        return Mapper.toPlanetDto(planet);
    }

    public List<PlanetDto> getAll() {
        return planetRepository.findAll()
                .stream()
                .map(Mapper::toPlanetDto)
                .toList();
    }
}
