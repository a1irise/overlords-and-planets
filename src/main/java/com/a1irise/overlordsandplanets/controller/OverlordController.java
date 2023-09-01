package com.a1irise.overlordsandplanets.controller;

import com.a1irise.overlordsandplanets.entity.Overlord;
import com.a1irise.overlordsandplanets.service.OverlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OverlordController {

    private OverlordService overlordService;

    @Autowired
    public OverlordController(OverlordService overlordService) {
        this.overlordService = overlordService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/overlord/add")
    public void add(@RequestParam(name = "name") String name, @RequestParam(name = "age") long age) {
        overlordService.addOverlord(name, age);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/overlord/get-top-ten-youngest")
    public void findTopTenYoungest() {
        List<Overlord> list =  overlordService.findTopTenYoungest();
        for (Overlord overlord : list) {
            System.out.println(overlord.getId());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/overlord/find-slackers")
    public void findSlackers() {
        List<Overlord> list =  overlordService.findSlackers();
        for (Overlord overlord : list) {
            System.out.println(overlord.getId());
        }
    }
}
