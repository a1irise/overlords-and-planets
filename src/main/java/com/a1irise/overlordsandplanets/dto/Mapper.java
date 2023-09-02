package com.a1irise.overlordsandplanets.dto;

import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;

public class Mapper {

    public static OverlordDto toOverlordDto(Overlord overlord) {
        return new OverlordDto(overlord.getName(), overlord.getAge());
    }

    public static Overlord toOverlord(OverlordDto overlordDto) {
        return new Overlord(overlordDto.getName(), overlordDto.getAge());
    }

    public static PlanetDto toPlanetDto(Planet planet) {
        return new PlanetDto(planet.getName());
    }

    public static Planet toPlanet(PlanetDto planetDto) {
        return new Planet(planetDto.getName());
    }
}
