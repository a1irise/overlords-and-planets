package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.dto.OverlordDto;
import com.a1irise.overlordsandplanets.service.OverlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/overlords")
public class OverlordController {

    private OverlordService overlordService;

    @Autowired
    public OverlordController(OverlordService overlordService) {
        this.overlordService = overlordService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addOverlord(@RequestBody OverlordDto overlordDto) {
        OverlordDto overlord = overlordService.addOverlord(overlordDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(overlord.getId())
                        .toUri())
                .build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> deleteOverlord(@PathVariable(name = "id") long id) {
        overlordService.deleteOverlord(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<OverlordDto> getById(@PathVariable(name = "id") long id) {
        OverlordDto overlord = overlordService.getById(id);
        return ResponseEntity.ok(overlord);
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<OverlordDto>> getAll() {
        List<OverlordDto> overlords = overlordService.getAll();
        return ResponseEntity.ok(overlords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/slackers")
    public ResponseEntity<List<OverlordDto>> findSlackers() {
        List<OverlordDto> overlords = overlordService.findSlackers();
        return ResponseEntity.ok(overlords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/top-ten-youngest")
    public ResponseEntity<List<OverlordDto>> findTopTenYoungest() {
        List<OverlordDto> overlords = overlordService.findTopTenYoungest();
        return ResponseEntity.ok(overlords);
    }
}
