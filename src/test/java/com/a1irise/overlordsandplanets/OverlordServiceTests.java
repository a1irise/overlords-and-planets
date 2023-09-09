package com.a1irise.overlordsandplanets;

import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.exception.OverlordAlreadyExistsException;
import com.a1irise.overlordsandplanets.exception.OverlordNotFoundException;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import com.a1irise.overlordsandplanets.repository.PlanetRepository;
import com.a1irise.overlordsandplanets.service.OverlordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    void shouldAddWhenNameIsFree() {
        Overlord overlord = new Overlord("Jerry", 37);
        OverlordDto expected = new OverlordDto("Jerry", 37);

        when(overlordRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(overlordRepository.save(any(Overlord.class))).thenReturn(overlord);

        OverlordDto actual = overlordService.addOverlord(expected);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findByName(anyString());
        verify(overlordRepository, times(1)).save(any(Overlord.class));
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenNameIsTaken() {
        Overlord overlord = new Overlord("Jerry", 37);
        OverlordDto overlordDto = new OverlordDto("Jerry", 37);

        when(overlordRepository.findByName(anyString())).thenReturn(Optional.of(overlord));

        assertThrows(OverlordAlreadyExistsException.class, () -> overlordService.addOverlord(overlordDto));
        verify(overlordRepository, times(1)).findByName(anyString());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldDeleteOverlord() {
        doNothing().when(overlordRepository).deleteById(anyLong());

        overlordService.deleteOverlord(1L);

        verify(overlordRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldFindByIdWhenOverlordExists() {
        Overlord overlord = new Overlord("Jerry", 37);
        OverlordDto expected = new OverlordDto("Jerry", 37);

        when(overlordRepository.findById(anyLong())).thenReturn(Optional.of(overlord));

        OverlordDto actual = overlordService.findById(1L);

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldThrowExceptionWhenOverlordDoesNotExist() {
        when(overlordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OverlordNotFoundException.class, () -> overlordService.findById(1L));
        verify(overlordRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldFindAll() {
        List<Overlord> overlords = List.of(new Overlord("Jerry", 37));
        List<OverlordDto> expected = List.of(new OverlordDto("Jerry", 37));

        when(overlordRepository.findAll()).thenReturn(overlords);

        List<OverlordDto> actual = overlordService.findAll();

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findAll();
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldReturnAListOfSlackers() {
        List<Overlord> overlords = List.of(new Overlord("Jerry", 37));
        List<OverlordDto> expected = List.of(new OverlordDto("Jerry", 37));

        when(overlordRepository.findAllSlackers()).thenReturn(overlords);

        List<OverlordDto> actual = overlordService.findSlackers();

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findAllSlackers();
        verifyNoMoreInteractions(overlordRepository);
    }

    @Test
    void shouldReturnAListOfTopTenYoungestOverlords() {
        List<Overlord> overlords = List.of(new Overlord("Jerry", 37));
        List<OverlordDto> expected = List.of(new OverlordDto("Jerry", 37));

        when(overlordRepository.findTop10ByOrderByAgeAsc()).thenReturn(overlords);

        List<OverlordDto> actual = overlordService.findTopTenYoungest();

        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
        verify(overlordRepository, times(1)).findTop10ByOrderByAgeAsc();
        verifyNoMoreInteractions(overlordRepository);
    }
}
