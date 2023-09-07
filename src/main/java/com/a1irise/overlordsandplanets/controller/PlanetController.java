package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.dto.PlanetDto;
import com.a1irise.overlordsandplanets.service.PlanetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/planets")
public class PlanetController {

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<Void> addPlanet(@RequestBody @Valid PlanetDto planetDto) {
        PlanetDto planet = planetService.addPlanet(planetDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(planet.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}/overlord")
    public ResponseEntity<Void> assignOverlord(@PathVariable(name = "id") long planetId,
                                               @RequestParam(name = "overlordId") long overlordId) {
        PlanetDto planet = planetService.assignOverlord(planetId, overlordId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable(name = "id") long id) {
        planetService.deletePlanet(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<PlanetDto> getById(@PathVariable(name = "id") long id) {
        PlanetDto planet = planetService.getById(id);
        return ResponseEntity.ok(planet);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<PlanetDto>> getAll() {
        List<PlanetDto> planets = planetService.getAll();
        return ResponseEntity.ok(planets);
    }
}
