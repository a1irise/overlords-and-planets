package com.a1irise.overlordsandplanets.dto;

import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;

public class Mapper {

    public static PlanetDto toPlanetDto(Planet planet) {
        OverlordDto overlordDto = planet.getOverlord() != null
                ? Mapper.toOverlordDto(planet.getOverlord())
                : null;

        return new PlanetDto(planet.getId(), planet.getName(), overlordDto);
    }

    public static Planet toPlanet(PlanetDto planetDto) {
        return new Planet(planetDto.getName());
    }

    public static OverlordDto toOverlordDto(Overlord overlord) {
        return new OverlordDto(overlord.getId(), overlord.getName(), overlord.getAge());
    }

    public static Overlord toOverlord(OverlordDto overlordDto) {
        return new Overlord(overlordDto.getName(), overlordDto.getAge());
    }
}
