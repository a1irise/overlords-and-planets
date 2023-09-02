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

    public void addPlanet(PlanetDto planetDto) {
        if (planetRepository.findByName(planetDto.getName()) != null) {
            throw new PlanetAlreadyExistsException("Planet with name \"" + planetDto.getName() + "\" already exists.");
        }

        Planet planet = Mapper.toPlanet(planetDto);
        planetRepository.save(planet);
    }

    public void destroyPlanet(PlanetDto planetDto) {
        if (planetRepository.deleteByName(planetDto.getName()) == 0) {
            throw new PlanetNotFoundException("Planet with name \"" + planetDto.getName() + "\" not found.");
        }
    }

    public void assignOverlord(String planetName, String overlordName) {
        Planet planet = planetRepository.findByName(planetName);
        Overlord overlord = overlordRepository.findByName(overlordName);

        if (planet == null) {
            throw new PlanetNotFoundException("Planet with name \"" + planetName + "\" not found.");
        } else if (overlord == null) {
            throw new OverlordNotFoundException("Overlord with name \"" + overlordName + "\" not found.");
        } else if (planet.getOverlord() != null) {
            throw new PlanetAlreadyHasOverlordException("Planet with name \"" + planetName + "\" already has an overlord.");
        }

        planet.setOverlord(overlord);
        planetRepository.save(planet);
    }
}
