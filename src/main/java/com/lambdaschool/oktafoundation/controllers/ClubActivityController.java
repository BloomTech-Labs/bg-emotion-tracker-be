package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubActivity;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clubactivities")
public class ClubActivityController {

    @Autowired
    private ClubActivityService clubActivityService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubactivities",
    produces = "application/json")
    public ResponseEntity<?> listAllClubActivities()
    {
        List<ClubActivity> allClubActivities = clubActivityService.findAll();
        return new ResponseEntity<>(allClubActivities, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubactivity/{clubactivityid}",
        produces = "application/json")
    public ResponseEntity<?> getClubActivityById(@PathVariable Long clubactivityid)
    {
        ClubActivity ca = clubActivityService.findClubActivityById(clubactivityid);
        return new ResponseEntity<>(ca,
                HttpStatus.OK);
    }
}
