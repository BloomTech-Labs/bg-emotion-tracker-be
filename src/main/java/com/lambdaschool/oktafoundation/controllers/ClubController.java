package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.UserService;
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
/**
 * The entry point for clients to access clubs data
 */
@RestController
@RequestMapping("/clubs")
public class ClubController {
    /**
     * Using the User service to process club data
     */
    @Autowired
    private ClubService clubService;
    /**
     * Returns a list of all clubs
     * <br>Example: <a href="http://localhost:2019/clubs/clubs"></a>
     *
     * @return JSON list of all clubs with a status of OK
     * @see ClubService#findAll() ClubService.findAll()
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubs",
    produces = "application/json")
    public ResponseEntity <?> ListAllClubs()
    {
        List<Club> allClubs = clubService.findAll();
        return new ResponseEntity<>(allClubs, HttpStatus.OK);

    }
    /**
     * Returns a single club based off a club id number
     * <br>Example: http://localhost:2019/clubs/club/7
     *
     * @param clubid The primary key of the club you seek
     * @return JSON object of the club you seek
     * @see ClubService#findClubById(Long)  ClubService.findClubById(long)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/club/{clubid}",
        produces = "application/json")
    public ResponseEntity<?> getClubById(@PathVariable Long clubid)
    {
        Club c = clubService.findClubById(clubid);
        return new ResponseEntity<>(c,
                HttpStatus.OK);
    }
    /**
     * Given a complete Club Object, create a new Club record
     * <br> Example: <a href="http://localhost:2019/users/user">http://localhost:2019/users/user</a>
     *
     * @param club A complete new club
     * @return A location header with the URI to the newly created club and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see ClubService#save(Club) ClubService.save(Club)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/user/newClub",
            consumes = "application/json")
    public ResponseEntity<?> addNewClub(
            @Valid @RequestBody Club club) throws URISyntaxException
    {
        club.setClubid(0);
        club = clubService.save(club);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newClubURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{clubid}")
                .buildAndExpand(club.getClubid())
                .toUri();
        responseHeaders.setLocation(newClubURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }
    /**
     * Given a complete Club Object
     * Given the club id, primary key, is in the Club table
     * <br> Example: <a href="http://localhost:2019/users/user/15">http://localhost:2019/users/user/15</a>
     *
     * @param club A complete Club to replace the Club
     * @param clubid     The primary key of the club you wish to replace.
     * @return status of OK
     * @see ClubService#save(Club) ClubService.save(Club)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping(value = "/club/{clubid}",
        consumes = "application/json")
    public ResponseEntity<?> updateClub(@RequestBody
                                        Club club,
                                        @PathVariable long clubid)
    {
        clubService.update(club,
                clubid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Deletes a given club
     * <br>Example: <a href="http://localhost:2019/clubs/club/14"></a>
     *
     * @param clubid the primary key of the club you wish to delete
     * @return Status of NO_CONTENT
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/club/{clubid}")
    public ResponseEntity<?> deleteClubById(
            @PathVariable long clubid)
    {
        clubService.delete(clubid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
