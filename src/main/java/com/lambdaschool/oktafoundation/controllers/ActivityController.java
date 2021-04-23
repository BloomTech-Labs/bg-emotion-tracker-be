package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping(value = "/activity",
    consumes = "application/json")
    public ResponseEntity<?> addNewActivity(
            @Valid @RequestBody Activity newActivity) throws URISyntaxException
    {
        newActivity.setActivityid(0);
        newActivity = activityService.save(newActivity);

//        Location Header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newActivityURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newActivity.getActivityid())
                .toUri();
        responseHeaders.setLocation(newActivityURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @PatchMapping(value = "/activity/{activityid}",
    consumes = "application/json")
    public ResponseEntity<?> updateActivity(@RequestBody Activity updateActivity, @PathVariable long activityid)
    {
        activityService.update(updateActivity, activityid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/activity/{activityid}")
    public ResponseEntity<?> deleteActivityById(@PathVariable long activityid)
    {
        activityService.delete(activityid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
