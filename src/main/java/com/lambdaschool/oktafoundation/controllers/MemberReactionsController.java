package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.models.Reaction;
import com.lambdaschool.oktafoundation.models.ValidationError;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.services.ActivityService;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * The entry point for clients to access memberreactions data
 */
@RestController
@RequestMapping("/memberreactions")
public class MemberReactionsController {
    /**
     * Using the MemberReactions service to process user data
     */
    @Autowired
    private MemberReactionService memberReactionService;

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubActivityRepository clubActivityRepository;

    @Autowired
    private ReactionRepository reactionRepository;


    /**
     * Returns a list of all memberreactions
     * <br>Example: <a href="http://localhost:2019/memberreactions/memberreactions"></a>
     *
     * @return JSON list of all memberreactions with a status of OK
     */
    @GetMapping(value = "/memberreactions",
    produces = "application/json")
    public ResponseEntity<?> listAllMemberReactions()
    {
        List<MemberReactions> allMemberReactions = memberReactionService.findAll();
        return new ResponseEntity<>(allMemberReactions, HttpStatus.OK);
    }
    /**
     * Returns a single memberreaction based off a memberreaction id number
     * <br>Example: http://localhost:2019/memberreactions/memberreaction/7
     *
     * @param id The primary key of the membereaction you seek
     * @return JSON object of the memberreaction you seek
     * @see MemberReactionService#
     */
    @GetMapping(value = "/memberreaction/{id}",
    produces = "application/json")
    public ResponseEntity<?> getMemberReactionById(@PathVariable Long id)
    {
        MemberReactions mr = memberReactionService.findMemberReactionById(id);
        return new ResponseEntity<>(mr, HttpStatus.OK);
    }


    @PostMapping(value = "/memberreaction/submit")
    public ResponseEntity<?> addNewReaction(
            @RequestParam(value = "mid") String mid,
            @RequestParam(value = "aid") Long aid,
            @RequestParam(value = "cid") Long cid,
            @RequestParam(value = "rx") String rx
            ) {

        var member = memberRepository.findMemberByMemberid(mid).orElseThrow();
        System.out.println(member.getMemberid());
        var ca = clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(
                aid,cid
        ).orElseThrow();
        Reaction currentreaction;
        try {
            currentreaction= reactionRepository.findReactionByReactionvalue(rx).orElseThrow();
        } catch (Exception e){
            currentreaction = new Reaction();
            currentreaction.setReactionvalue(rx);
            currentreaction=reactionRepository.save(currentreaction);
        }
        MemberReactions temp = new MemberReactions(member,currentreaction,true, ca);

        temp = memberReactionRepository.save(temp);


        return new ResponseEntity<>(HttpStatus.OK);

    }


}
