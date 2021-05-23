package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import com.lambdaschool.oktafoundation.services.MemberService;
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
 * The entry point for clients to access member data
 */
@RestController
@RequestMapping("/members")
public class MemberController {
    /**
     * Using the Member service to process member data
     */
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    /**
     * Returns a list of all Members.
     *
     * @return JSON list of all members with a status of OK
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @GetMapping(value = "/members", produces = "application/json")
    public ResponseEntity<?> listAllMembers() {
        List<Member> allMembers = memberService.findAll();
        return new ResponseEntity<>(allMembers, HttpStatus.OK);
    }


   /**
     * Returns whether the Member with given id exists in DB
     *
     * @param mid The member's ID value (Username).
     * @return a boolean value
     */
    @GetMapping(value="/check")
    public ResponseEntity<?> checkMember(@RequestParam(value = "mid") String mid){
        return new ResponseEntity<>(memberRepository.findMemberByMemberid(mid).isPresent(),HttpStatus.OK);
    }

    /**
     * Returns the Member with the given id.
     *
     * @param member_table_id The primary key of the Member you seek
     * @return JSON object of the Member you seek
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @GetMapping(value = "/member/{member_table_id}", produces = "application/json")
    public ResponseEntity<?> getMemberById(@PathVariable Long member_table_id) {
        Member m = memberService.findMemberById(member_table_id);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    /**
     * Given a complete Member object, creates a new Member record.
     *
     * @param newMember The new Member to be added
     * @return A location header with the URI to the newly created member and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the response header
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @PostMapping(value = "/member", consumes = "application/json")
    public ResponseEntity<?> addNewMember(@Valid @RequestBody Member newMember) throws URISyntaxException {
        newMember.setMember_table_id(0);
        newMember = memberService.save(newMember);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newMemberURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{member_table_id}")
                .buildAndExpand(newMember.getMember_table_id())
                .toUri();
        responseHeaders.setLocation(newMemberURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
