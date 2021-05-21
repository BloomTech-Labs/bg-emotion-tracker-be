package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubUsers;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import com.lambdaschool.oktafoundation.services.ClubUsersService;
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
 * The entry point for clients to access clubusers data
 */
@RestController
@RequestMapping("/clubusers")
public class ClubUsersController {
    /**
     * Using the ClubUsers service to process clubusers data
     */
    @Autowired
    private ClubUsersService clubUsersService;

    /**
     * Returns a list of all Clubusers.
     * <br>Example: <a href="http://localhost:2019/clubusers/clubusers"></a>
     *
     * @return JSON list of all Clubusers with a status of OK
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubusers",
    produces = "application/json")
    public ResponseEntity<?> listAllClubUsers()
    {
        List<ClubUsers> allClubUsers = clubUsersService.findAll();
        return new ResponseEntity<>(allClubUsers, HttpStatus.OK);
    }

    /**
     * Returns the ClubUsers object with the given id.
     * <br>Example: http://localhost:2019/clubusers/clubusers/7
     *
     * @param clubuserid The primary key of the ClubUser you seek
     * @return JSON object of the ClubUser you seek
     * @see ClubUsersService
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubuser/{clubuserid}",
    produces = "application/json")
    public ResponseEntity<?> getClubUserById(@PathVariable Long clubuserid)
    {
        ClubUsers cu = clubUsersService.findClubUserById(clubuserid);
        return new ResponseEntity<>(cu,
                HttpStatus.OK);
    }
}
