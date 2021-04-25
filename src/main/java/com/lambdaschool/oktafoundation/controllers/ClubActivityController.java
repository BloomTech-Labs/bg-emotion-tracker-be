package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import com.lambdaschool.oktafoundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * The entry point for clients to access clubactivities data
 */
@RestController
@RequestMapping("/clubactivities")
public class ClubActivityController {
    /**
     * Using the ClubActivities service to process user data
     */
    @Autowired
    private ClubActivityService clubActivityService;
    /**
     * Returns a list of all clubactivities
     * <br>Example: <a href="http://localhost:2019/clubactivities/clubactivities"></a>
     *
     * @return JSON list of all clubactivities with a status of OK
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubactivities",
    produces = "application/json")
    public ResponseEntity<?> listAllClubActivities()
    {
        List<ClubActivities> allClubActivities = clubActivityService.findAll();
        return new ResponseEntity<>(allClubActivities, HttpStatus.OK);
    }
    /**
     * Returns a single clubactivity based off a clubactivity id number
     * <br>Example: http://localhost:2019/clubactivities/clubactivity/7
     *
     * @param clubactivityid The primary key of the clubactivity you seek
     * @return JSON object of the clubactivity you seek
     * @see ClubActivityService#findClubActivityById(Long)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubactivity/{clubactivityid}",
        produces = "application/json")
    public ResponseEntity<?> getClubActivityById(@PathVariable Long clubactivityid)
    {
        ClubActivities ca = clubActivityService.findClubActivityById(clubactivityid);
        return new ResponseEntity<>(ca,
                HttpStatus.OK);
    }
}
