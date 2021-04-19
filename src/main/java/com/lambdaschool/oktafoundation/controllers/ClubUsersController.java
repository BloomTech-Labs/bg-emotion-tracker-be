package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubUsers;
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

@RestController
@RequestMapping("/clubusers")
public class ClubUsersController {

    @Autowired
    private ClubUsersService clubUsersService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubusers",
    produces = "application/json")
    public ResponseEntity<?> listAllClubUsers()
    {
        List<ClubUsers> allClubUsers = clubUsersService.findAll();
        return new ResponseEntity<>(allClubUsers, HttpStatus.OK);
    }

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
