package com.a1irise.overlordsandplanets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto {

    private long id;
    private String name;
    private OverlordDto overlordDto;
}
