package com.a1irise.overlordsandplanets.service;

import com.a1irise.overlordsandplanets.dto.Mapper;
import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.exception.OverlordAlreadyExistsException;
import com.a1irise.overlordsandplanets.exception.OverlordNotFoundException;
import com.a1irise.overlordsandplanets.repository.OverlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OverlordService {

    private OverlordRepository overlordRepository;

    @Autowired
    public OverlordService(OverlordRepository overlordRepository) {
        this.overlordRepository = overlordRepository;
    }

    public OverlordDto addOverlord(OverlordDto overlordDto) {
        if (overlordRepository.findByName(overlordDto.getName()).isPresent()) {
            throw new OverlordAlreadyExistsException("Overlord already exists.");
        }

        return Mapper.toOverlordDto(overlordRepository.save(Mapper.toOverlord(overlordDto)));
    }

    public void deleteOverlord(long id) {
        overlordRepository.findById(id).ifPresent(overlordRepository::delete);
    }

    public OverlordDto getById(long id) {
        Overlord overlord = overlordRepository.findById(id)
                .orElseThrow(()-> new OverlordNotFoundException("Overlord not found."));

        return Mapper.toOverlordDto(overlord);
    }

    public List<OverlordDto> getAll() {
        return overlordRepository.findAll()
                .stream()
                .map(Mapper::toOverlordDto)
                .toList();
    }

    public List<OverlordDto> findSlackers() {
        return overlordRepository.findAllSlackers()
                .stream()
                .map(Mapper::toOverlordDto)
                .toList();
    }

    public List<OverlordDto> findTopTenYoungest() {
        return overlordRepository.findTop10ByOrderByAgeAsc()
                .stream()
                .map(Mapper::toOverlordDto)
                .toList();
    }
}
