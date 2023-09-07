package com.a1irise.overlordsandplanets.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OverlordDto {

    private long id;

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[a-zA-z]+", message = "Name should only contain English letters")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    @NotEmpty(message = "Age should not be empty")
    @Pattern(regexp = "[0-9]+", message = "Age should be a number")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 1000000, message = "Age should not be greater than 1000000")
    private int age;
}
