package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubs",
    produces = "application/json")
    public ResponseEntity <?> ListAllClubs()
    {
        List<Club> allClubs = clubService.findAll();
        return new ResponseEntity<>(allClubs, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/club/{clubid}",
        produces = "application/json")
    public ResponseEntity<?> getClubById(@PathVariable Long clubid)
    {
        Club c = clubService.findClubById(clubid);
        return new ResponseEntity<>(c,
                HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/user/newClub",
            consumes = "application/json")
    public ResponseEntity<?> addNewClub(
            @Valid @RequestBody Club newClub) throws URISyntaxException
    {
        newClub.setClubid(0);
        newClub = clubService.save(newClub);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newClubURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{clubid}")
                .buildAndExpand(newClub.getClubid())
                .toUri();
        responseHeaders.setLocation(newClubURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/club/{clubid}",
        consumes = "application/json")
    public ResponseEntity<?> updateClub(@RequestBody
                                        Club updateClub,
                                        @PathVariable long clubid)
    {
        clubService.update(updateClub,
                clubid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
