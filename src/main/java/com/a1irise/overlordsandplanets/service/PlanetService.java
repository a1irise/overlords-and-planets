package com.a1irise.overlordsandplanets.service;

import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

    private PlanetRepository planetRepository;
    private OverlordRepository overlordRepository;

    @Autowired
    public PlanetService(PlanetRepository planetRepository, OverlordRepository overlordRepository) {
        this.planetRepository = planetRepository;
        this.overlordRepository = overlordRepository;
    }

    public void addPlanet(String name) {
        Planet planet = new Planet(name);
        planetRepository.save(planet);
    }

    public void destroyPlanet(String name) {
        planetRepository.deleteByName(name);
    }

    public void assignOverlord(String planetName, String overlordName) {
        Planet planet = planetRepository.findByName(planetName);
        Overlord overlord = overlordRepository.findByName(overlordName);
        planet.setOverlord(overlord);
        planetRepository.save(planet);
    }
}
