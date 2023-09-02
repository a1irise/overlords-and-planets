package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.exception.OverlordAlreadyExistsException;
import com.a1irise.overlordsandplanets.service.OverlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/api/overlord")
public class OverlordController {

    private OverlordService overlordService;

    @Autowired
    public OverlordController(OverlordService overlordService) {
        this.overlordService = overlordService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<String> add(@RequestBody OverlordDto overlordDto) {
        try {
            overlordService.addOverlord(overlordDto);
        } catch (OverlordAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Overlord with name \"" + overlordDto.getName() + "\" already exists.");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Overlord with name \"" + overlordDto.getName() + "\" added successfully.");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find-slackers")
    public ResponseEntity<List<OverlordDto>> findSlackers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(overlordService.findSlackers());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-top-ten-youngest")
    public ResponseEntity<List<OverlordDto>> findTopTenYoungest() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(overlordService.findTopTenYoungest());
    }
}
