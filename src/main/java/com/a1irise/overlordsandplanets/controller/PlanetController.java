package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.dto.PlanetDto;
import com.a1irise.overlordsandplanets.exception.OverlordNotFoundException;
import com.a1irise.overlordsandplanets.exception.PlanetAlreadyExistsException;
import com.a1irise.overlordsandplanets.exception.PlanetAlreadyHasOverlordException;
import com.a1irise.overlordsandplanets.exception.PlanetNotFoundException;
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
        try {
            planetService.addPlanet(planetDto);
        } catch (PlanetAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Planet with name \"" + planetDto.getName() + "\" already exists.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Planet with name \"" + planetDto.getName() + "\" added successfully.");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/destroy")
    public ResponseEntity<String> destroy(@RequestBody PlanetDto planetDto) {
        try {
            planetService.destroyPlanet(planetDto);
        } catch (PlanetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Planet with name \"" + planetDto.getName() + "\" not found.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Planet with name \"" + planetDto.getName() + "\" destroyed successfully.");
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/assign-overlord")
    public ResponseEntity<String> assignOverlord(@RequestParam(name = "planetName") String planetName,
                                                 @RequestParam(name = "overlordName") String overlordName) {
        try {
            planetService.assignOverlord(planetName, overlordName);
        } catch (PlanetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Planet with name \"" + planetName + "\" not found.");
        } catch (OverlordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Overlord with name \"" + overlordName + "\" not found.");
        } catch (PlanetAlreadyHasOverlordException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Planet with name \"" + planetName + "\" already has overlord.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Overlord with name \"" + overlordName +
                        "\" successfully assigned to planet with name \"" + planetName +
                        "\".");
    }
}
