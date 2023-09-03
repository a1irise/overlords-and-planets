package com.a1irise.overlordsandplanets;

import com.a1irise.overlordsandplanets.dto.Mapper;
import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.entity.Planet;
import com.a1irise.overlordsandplanets.exception.OverlordAlreadyExistsException;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import com.a1irise.overlordsandplanets.service.OverlordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OverlordServiceTests {

    @Autowired
    OverlordService overlordService;

    @MockBean
    OverlordRepository overlordRepository;
    @MockBean
    PlanetRepository planetRepository;

    @Test
    void shouldAddOverlordWhenNameIsFree() {
        Overlord overlord = new Overlord("Lord Farquaad", 54);
        OverlordDto expected = new OverlordDto("Lord Farquaad", 54);

        when(overlordRepository.existsByName(anyString())).thenReturn(false);
        when(overlordRepository.save(any(Overlord.class))).thenReturn(overlord);

        OverlordDto actual = overlordService.addOverlord(expected);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).existsByName(anyString());
        verify(overlordRepository, times(1)).save(any(Overlord.class));
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTaken() {
        OverlordDto overlordDto = new OverlordDto("Lord Farquaad", 54);

        when(overlordRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(OverlordAlreadyExistsException.class, () -> overlordService.addOverlord(overlordDto));
        verify(overlordRepository, times(1)).existsByName(anyString());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldReturnAListOfSlackers() {
        Overlord overlord1 = new Overlord("Lord Farquaad", 54);
        Overlord overlord2 = new Overlord("Davos Seaworth", 77);
        Planet planet = new Planet(1, "Mars", overlord1);
        List<Overlord> overlords = List.of(overlord1, overlord2);
        List<Planet> planets = List.of(planet);

        when(overlordRepository.findAll()).thenReturn(overlords);
        when(planetRepository.findAll()).thenReturn(planets);

        List<OverlordDto> expected = List.of(Mapper.toOverlordDto(overlord2));
        List<OverlordDto> actual = overlordService.findSlackers();

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findAll();
        verifyNoMoreInteractions(overlordRepository);
        verify(planetRepository, times(1)).findAll();
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldReturnAnEmptyListWhenNoOverlordsHaveBeenAdded() {
        List<Overlord> overlords = new ArrayList<>();
        List<Planet> planets = new ArrayList<>();

        when(overlordRepository.findAll()).thenReturn(overlords);
        when(planetRepository.findAll()).thenReturn(planets);

        List<OverlordDto> actual = overlordService.findSlackers();

        assertEquals(0, actual.size());
        verify(overlordRepository, times(1)).findAll();
        verifyNoMoreInteractions(overlordRepository);
        verify(planetRepository, times(1)).findAll();
        verifyNoMoreInteractions(planetRepository);
    }

    @Test
    void shouldReturnAListOfTopTenYoungestOverlord() {
        Overlord overlord1 = new Overlord("Lord Farquaad", 54);
        Overlord overlord2 = new Overlord("Davos Seaworth", 77);
        List<Overlord> overlords = List.of(overlord1, overlord2);

        when(overlordRepository.findWithPageable(any(PageRequest.class))).thenReturn(overlords);

        List<OverlordDto> expected = List.of(Mapper.toOverlordDto(overlord1), Mapper.toOverlordDto(overlord2));
        List<OverlordDto> actual = overlordService.findTopTenYoungest();

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findWithPageable(any(PageRequest.class));
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldReturnAnEmptyListWhenNoOverlordsHaveBeenAdded2() {
        List<Overlord> overlords = new ArrayList<>();

        when(overlordRepository.findWithPageable(any(PageRequest.class))).thenReturn(overlords);

        List<OverlordDto> actual = overlordService.findTopTenYoungest();

        assertEquals(0, actual.size());
        verify(overlordRepository, times(1)).findWithPageable(any(PageRequest.class));
        verifyNoMoreInteractions(overlordRepository);
    }
}
