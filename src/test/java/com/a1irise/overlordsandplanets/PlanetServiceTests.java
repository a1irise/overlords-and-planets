package com.a1irise.overlordsandplanets;

import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.dto.PlanetDto;
import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;
import com.a1irise.overlordsandplanets.exception.OverlordNotFoundException;
import com.a1irise.overlordsandplanets.exception.PlanetAlreadyExistsException;
import com.a1irise.overlordsandplanets.exception.PlanetAlreadyHasOverlordException;
import com.a1irise.overlordsandplanets.exception.PlanetNotFoundException;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import com.a1irise.overlordsandplanets.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlanetServiceTests {

    @Autowired
    PlanetService planetService;

    @MockBean
    PlanetRepository planetRepository;
    @MockBean
    OverlordRepository overlordRepository;

    @Test
    void shouldAddPlanetWhenNameIsFree() {
        Planet planet = new Planet("Mars");
        PlanetDto expected = new PlanetDto("Mars");

        when(planetRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(planetRepository.save(any(Planet.class))).thenReturn(planet);

        PlanetDto actual = planetService.addPlanet(expected);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(planetRepository, times(1)).findByName(anyString());
        verify(planetRepository, times(1)).save(any(Planet.class));
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTaken() {
        Planet planet = new Planet("Mars");
        PlanetDto planetDto = new PlanetDto("Mars");

        when(planetRepository.findByName(anyString())).thenReturn(Optional.of(planet));

        assertThrows(PlanetAlreadyExistsException.class, () -> planetService.addPlanet(planetDto));
        verify(planetRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldAssignOverlordWhenPlanetExistsAndDoesNotHaveAnOverlordAndOverlordExists() {
        Planet planet = new Planet("Mars");
        Overlord overlord = new Overlord("Jerry", 37);
        OverlordDto overlordDto = new OverlordDto("Jerry", 37);

        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(planet));
        when(overlordRepository.findById(anyLong())).thenReturn(Optional.of(overlord));

        Planet planetWithOverlord = new Planet(1, "Mars", overlord);

        when(planetRepository.save(any(Planet.class))).thenReturn(planetWithOverlord);

        PlanetDto expected = new PlanetDto(1, "Mars", overlordDto);
        PlanetDto actual = planetService.assignOverlord(1, 1);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(planetRepository, times(1)).findById(anyLong());
        verify(planetRepository, times(1)).save(any(Planet.class));
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenPlanetNotFound() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PlanetNotFoundException.class, () -> planetService.assignOverlord(1, 1));
        verify(planetRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(planetRepository);
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenOverlordNotFound() {
        Planet planet = new Planet("Mars");

        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(planet));
        when(overlordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OverlordNotFoundException.class, () -> planetService.assignOverlord(1, 1));
        verify(planetRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenPlanetAlreadyHasAnOverlord() {
        Overlord overlord = new Overlord("Jerry", 37);
        Planet planet = new Planet(1, "Mars", overlord);

        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(planet));
        when(overlordRepository.findById(anyLong())).thenReturn(Optional.of(overlord));

        assertThrows(PlanetAlreadyHasOverlordException.class, () -> planetService.assignOverlord(1, 1));
        verify(planetRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldDeletePlanet() {
        doNothing().when(planetRepository).deleteById(anyLong());

        planetService.deletePlanet(1L);

        verify(planetRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldFindByIdWhenPlanetExists() {
        Planet planet = new Planet("Mars");
        PlanetDto expected = new PlanetDto("Mars");

        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(planet));

        PlanetDto actual = planetService.findById(1L);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(planetRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldThrowExceptionWhenPlanetDoesNotExist() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PlanetNotFoundException.class, () -> planetService.findById(1L));
        verify(planetRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldFindAll() {
        List<Planet> planets = List.of(new Planet("Mars"));
        List<PlanetDto> expected = List.of(new PlanetDto("Mars"));

        when(planetRepository.findAll()).thenReturn(planets);

        List<PlanetDto> actual = planetService.findAll();

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(planetRepository, times(1)).findAll();
        verifyNoMoreInteractions(planetRepository);
    }
}
