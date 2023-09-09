package com.a1irise.overlordsandplanets.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[a-zA-z]+", message = "Name can only contain English letters")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    private OverlordDto overlord;

    public PlanetDto(String name) {
        this.name = name;
    }
}
