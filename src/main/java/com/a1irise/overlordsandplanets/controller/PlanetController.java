package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlanetController {

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/planet/add")
    public void add(@RequestParam(name = "name") String name) {
        planetService.addPlanet(name);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/planet/destroy")
    public void destroy(@RequestParam(name = "name") String name) {
        planetService.destroyPlanet(name);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/api/planet/assign-overlord")
    public void assignOverlord(@RequestParam(name = "planetName") String planetName, @RequestParam(name = "overlordName") String overlordName) {
        planetService.assignOverlord(planetName, overlordName);
    }
}
