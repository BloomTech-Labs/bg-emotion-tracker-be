package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping(value = "/activities",
    produces = "application/json")
    public ResponseEntity<?> listAllActivities()
    {
        List<Activity> allActivities = activityService.findAll();
        return new ResponseEntity<>(allActivities, HttpStatus.OK);
    }

    @GetMapping(value = "/activity/{activityid}",
    produces = "application/json")
    public ResponseEntity<?> getActivityById(@PathVariable Long activityid)
    {
        Activity a = activityService.findActivityById(activityid);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
