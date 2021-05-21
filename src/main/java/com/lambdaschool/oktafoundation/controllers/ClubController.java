package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubMembers;
import com.lambdaschool.oktafoundation.models.ClubUsers;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.ClubUsersRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberService;
import com.lambdaschool.oktafoundation.services.UserService;
import com.lambdaschool.oktafoundation.views.ClubSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.awt.desktop.OpenFilesEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * The entry point for clients to access club data
 */
@RestController
@RequestMapping("/clubs")
public class ClubController {
    /**
     * Using the Club service to process club data
     */
    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClubUsersRepository clubUsersRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ClubMembersRepository clubMembersRepository;

    /**
     * Returns a list of all Clubs.
     * <br>Example: <a href="http://localhost:2019/clubs/clubs"></a>
     *
     * @return JSON list of all Clubs with a status of OK
     * @see ClubService#findAll() ClubService.findAll()
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/clubs",
    produces = "application/json")
    public ResponseEntity <?> ListAllClubs()
    {
        List<Club> allClubs = clubService.findAll();
        return new ResponseEntity<>(allClubs, HttpStatus.OK);

    }

    /**
     * Returns the Club with the given id.
     * <br>Example: http://localhost:2019/clubs/club/7
     *
     * @param clubid The primary key of the club you seek
     * @return JSON object of the Club you seek
     * @see ClubService#findClubById(Long)  ClubService.findClubById(long)
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @GetMapping(value = "/club/{clubid}",
        produces = "application/json")
    public ResponseEntity<?> getClubById(@PathVariable Long clubid)
    {
        Club c = clubService.findClubById(clubid);
        return new ResponseEntity<>(c,
                HttpStatus.OK);
    }

    /**
     * Given a complete Club object, creates a new Club.
     * <br> Example: <a href="http://localhost:2019/users/user">http://localhost:2019/users/user</a>
     *
     * @param club A complete new Club to be added
     * @return A location header with the URI to the newly created Club and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see ClubService#save(Club) ClubService.save(Club)
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @PostMapping(value = "/club/newClub",
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
     * Updates the Club record with the given id using the provided data
     * <br> Example: <a href="http://localhost:2019/users/user/15">http://localhost:2019/users/user/15</a>
     *
     * @param club An object containing values for just the fields being updated, all other fields left NULL
     * @param clubid     The primary key of the Club you wish to replace
     * @return status of OK
     * @see ClubService#save(Club) ClubService.save(Club)
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
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
     * Deletes the Club with the given id.
     * <br>Example: <a href="http://localhost:2019/clubs/club/14"></a>
     *
     * @param clubid The primary key of the Club you wish to delete
     * @return Status of NO_CONTENT
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @DeleteMapping(value = "/club/{clubid}")
    public ResponseEntity<?> deleteClubById(
            @PathVariable long clubid)
    {
        clubService.delete(clubid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Returns a summary of all existing Clubs, including name and id.
     *
     * @return A summary of all Clubs, including name and id
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @GetMapping(value = "/summary")
    public ResponseEntity<?> getClubsSummary(){
        List<ClubSummary> temp = clubRepository.getClubsSummary();
        return new ResponseEntity<>(temp,HttpStatus.OK);
    }

    /**
     * Adds the User with the given userId to the Club with the given clubId.
     *
     * @param cid The id of the Club to which the User should be added
     * @param uid The id of the User to be added
     * @return Status of OK
     * @throws URISyntaxException
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @PostMapping(value = "/club/{cid}/addUser/{uid}",
            consumes = "application/json")
    public ResponseEntity<?> addNewUser(
            @PathVariable Long cid, @PathVariable Long uid) throws URISyntaxException
    {
        var currentclub = clubService.findClubById(cid);
        var currentuser = userService.findUserById(uid);
        currentclub.getUsers().add(new ClubUsers(currentclub,currentuser));

        // services currently has problem with its save.
        clubRepository.save(currentclub);

        return new ResponseEntity<>(null,
                HttpStatus.OK);
    }

    /**
     * Removes the User with the given userId from the Club with the given clubId.
     *
     * @param cid The id of the Club from which the User should be removed
     * @param uid The id of the User to be removed
     * @return Status of OK
     * @throws URISyntaxException
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @DeleteMapping(value = "/club/{cid}/removeUser/{uid}")
    public ResponseEntity<?> removeNewUser(
            @PathVariable Long cid, @PathVariable Long uid) throws URISyntaxException
    {
        var cu = clubUsersRepository.findClubUsersByClub_ClubidAndUser_Userid(cid,uid).orElseThrow();
        clubUsersRepository.delete(cu);

        return new ResponseEntity<>(null,
                HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @PostMapping(value = "/club/{cid}/addMember/{mid}")
    public ResponseEntity<?> addNewMemberToClub(@PathVariable Long cid, @PathVariable String mid){
        var club = clubService.findClubById(cid);
        var member = memberService.findMemberByMemberId(mid);

        clubMembersRepository.save(new ClubMembers(club,member));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @DeleteMapping(value = "/club/{cid}/removeMember/{mid}")
    public ResponseEntity<?> removeMemberFromClub(
            @PathVariable Long cid, @PathVariable String mid) throws URISyntaxException
    {
        var cm = clubMembersRepository.findClubMembersByClub_ClubidAndMember_Memberid(cid,mid);
        if(cm.isPresent()){
            clubMembersRepository.delete(cm.get());
            return new ResponseEntity<>(null,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Such Relationship",
                    HttpStatus.NOT_MODIFIED);
        }
    }
}
