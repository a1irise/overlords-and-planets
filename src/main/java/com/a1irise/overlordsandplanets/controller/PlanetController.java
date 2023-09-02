package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.dto.PlanetDto;
import com.a1irise.overlordsandplanets.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api/planet")
public class PlanetController {

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<String> add(@RequestBody PlanetDto planetDto) {
        planetService.addPlanet(planetDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Planet with name \"" + planetDto.getName() + "\" added successfully.");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/destroy")
    public ResponseEntity<String> destroy(@RequestBody PlanetDto planetDto) {
        planetService.destroyPlanet(planetDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Planet with name \"" + planetDto.getName() + "\" destroyed successfully.");
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/assign-overlord")
    public ResponseEntity<String> assignOverlord(@RequestParam(name = "planetName") String planetName,
                                                 @RequestParam(name = "overlordName") String overlordName) {
        planetService.assignOverlord(planetName, overlordName);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Overlord with name \"" + overlordName +
                        "\" successfully assigned to planet with name \"" + planetName + "\".");
    }
}
