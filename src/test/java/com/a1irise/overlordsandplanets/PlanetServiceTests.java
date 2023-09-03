package com.a1irise.overlordsandplanets;

import com.a1irise.overlordsandplanets.dto.Mapper;
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

        when(planetRepository.existsByName(anyString())).thenReturn(false);
        when(planetRepository.save(any(Planet.class))).thenReturn(planet);

        PlanetDto actual = planetService.addPlanet(expected);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(planetRepository, times(1)).existsByName(anyString());
        verify(planetRepository, times(1)).save(any(Planet.class));
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTaken() {
        PlanetDto planetDto = new PlanetDto("Mars");

        when(planetRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(PlanetAlreadyExistsException.class, () -> planetService.addPlanet(planetDto));
        verify(planetRepository, times(1)).existsByName(anyString());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldDeletePlanetWhenItExists() {
        PlanetDto planetDto = new PlanetDto("Mars");

        when(planetRepository.existsByName(anyString())).thenReturn(true);
        doNothing().when(planetRepository).deleteByName(anyString());

        planetService.destroyPlanet(planetDto);

        verify(planetRepository, times(1)).existsByName(anyString());
        verify(planetRepository, times(1)).deleteByName(anyString());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldThrowExceptionWhenPlanetDoesNotExist() {
        PlanetDto planetDto = new PlanetDto("Mars");

        when(planetRepository.existsByName(anyString())).thenReturn(false);

        assertThrows(PlanetNotFoundException.class, () -> planetService.destroyPlanet(planetDto));
        verify(planetRepository, times(1)).existsByName(anyString());
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldAssignOverlordWhenPlanetExistsAndDoesNotHaveAnOverlordAndOverlordExists() {
        Planet planet = new Planet("Mars");
        Overlord overlord = new Overlord("Lord Farquaad", 54);

        when(planetRepository.findByName(anyString())).thenReturn(planet);
        when(overlordRepository.findByName(anyString())).thenReturn(overlord);

        Planet planetWithOverlord = new Planet(1, "Mars", overlord);

        when(planetRepository.save(any(Planet.class))).thenReturn(planetWithOverlord);

        PlanetDto expected = Mapper.toPlanetDto(planetWithOverlord);
        PlanetDto actual = planetService.assignOverlord(planet.getName(), overlord.getName());

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(planetRepository, times(1)).findByName(anyString());
        verify(planetRepository, times(1)).save(any(Planet.class));
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenPlanetNotFound() {
        Planet planet = new Planet("Mars");
        Overlord overlord = new Overlord("Lord Farquaad", 54);

        when(planetRepository.findByName(anyString())).thenReturn(null);
        when(overlordRepository.findByName(anyString())).thenReturn(overlord);

        assertThrows(PlanetNotFoundException.class,
                () -> planetService.assignOverlord(planet.getName(), overlord.getName()));
        verify(planetRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenOverlordNotFound() {
        Planet planet = new Planet("Mars");
        Overlord overlord = new Overlord("Lord Farquaad", 54);

        when(planetRepository.findByName(anyString())).thenReturn(planet);
        when(overlordRepository.findByName(anyString())).thenReturn(null);

        assertThrows(OverlordNotFoundException.class,
                () -> planetService.assignOverlord(planet.getName(), overlord.getName()));
        verify(planetRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenPlanetAlreadyHasAnOverlord() {
        Overlord overlord = new Overlord("Lord Farquaad", 54);
        Planet planet = new Planet(1, "Mars", overlord);

        when(planetRepository.findByName(anyString())).thenReturn(planet);
        when(overlordRepository.findByName(anyString())).thenReturn(overlord);

        assertThrows(PlanetAlreadyHasOverlordException.class,
                () -> planetService.assignOverlord(planet.getName(), overlord.getName()));
        verify(planetRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(planetRepository);
        verify(overlordRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(overlordRepository);
    }
}
