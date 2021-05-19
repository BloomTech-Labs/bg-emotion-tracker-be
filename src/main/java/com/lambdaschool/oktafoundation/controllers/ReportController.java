package com.lambdaschool.oktafoundation.controllers;


import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import com.lambdaschool.oktafoundation.views.ClubActivityPosivity;
import com.lambdaschool.oktafoundation.views.MemberPosivity;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    private HashMap<String,Integer> emojimap;

    public ReportController(){
        emojimap = new HashMap<>();
        emojimap.put("1F601", 2);
        emojimap.put("1F642", 1);
        emojimap.put("1F610", 0);
        emojimap.put("1F641", -1);
        emojimap.put("1F61E", -2);
    }

    @Autowired
    private MemberReactionService memberReactionService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private EntityManager entityManager;

    /* The endpoints shall serve data analysis of the member reactions
    with respect to club/activity/member.
     */

    // Currently we have the simple feature of calculating the avgs of positivity value of all members
    // and avgs of the received posivity regarding activities in a club.


    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/members")
    public ResponseEntity<?> getPosivitiesByMembers(
            @PathVariable Long cid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to){

        var fromdate = from;
        var todate = to;
        if(from == null){
            fromdate = "1000-01-01";
        }
        if(to == null){
            todate = "3000-01-01";
        }

        // whether we guard against certain club
        // if cid = 0; we search all members
        var clubfilter = "";
        if (cid.intValue() != 0){
            clubfilter = "and clubid = " + cid.toString()+ " ";
        }


        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'"  + clubfilter,MemberReactions.class);
        List<MemberReactions> mrlist = q.getResultList();
        var prememberPositivty = new HashMap<String, ArrayList<Integer>>();
//        System.out.println(mrlist.get(0));
        mrlist.forEach(e -> {
            if(prememberPositivty.containsKey(e.getMember().getMemberid())){
                prememberPositivty.get(e.getMember().getMemberid()).add(emojimap.get(e.getReaction().getReactionvalue()));
            } else {
                var templ = new ArrayList<Integer> ();
                templ.add(emojimap.get(e.getReaction().getReactionvalue()));
                prememberPositivty.put(e.getMember().getMemberid(),templ);
            }
        });

        List<MemberPosivity> mplist = new ArrayList<>();
        var members = new ArrayList<>(prememberPositivty.keySet());
        for (var member: members) {
            var temp = new MemberPosivity();
            temp.setMemberid(member);
            var memberreactions = prememberPositivty.get(member);
            var len = memberreactions.size();
            temp.setPosivitiy(memberreactions.stream().reduce((a,b) -> a+b).get()/(double)len);
            mplist.add(temp);
        }


        return new ResponseEntity<>(mplist,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/activities")
    public ResponseEntity<?> getPosivitiesByClub(
        @PathVariable Long cid,
        @RequestParam(value = "from", required = false) String from,
        @RequestParam(value = "to", required = false) String to
    ){

        var fromdate = from;
        var todate = to;
        if(from == null){
            fromdate = "1000-01-01";
        }
        if(to == null){
            todate = "3000-01-01";
        }

        // whether we guard against certain club
        // if cid = 0; we search all members
        var clubfilter = "";
        if (cid.intValue() != 0){
            clubfilter = "and clubid = " + cid.toString()+ " ";
        }


        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'" + clubfilter,MemberReactions.class);
        List<MemberReactions> mrlist = q.getResultList();
        var preactivityPositivity = new HashMap<List<String>, ArrayList<Integer>>();
        mrlist.forEach(e -> {
            var ckey = new ArrayList<String>();
            ckey.add(e.getClubactivity().getClub().getClubname());
            ckey.add(e.getClubactivity().getActivity().getActivityname());
            if(preactivityPositivity.containsKey(ckey)){
                preactivityPositivity.get(ckey).add(emojimap.get(e.getReaction().getReactionvalue()));
            } else {
                var templ = new ArrayList<Integer> ();
                templ.add(emojimap.get(e.getReaction().getReactionvalue()));
                preactivityPositivity.put(ckey,templ);
            }
        });

        List<ClubActivityPosivity> caplist = new ArrayList<>();
        var activities = new ArrayList<>(preactivityPositivity.keySet());
        for (var ca: activities) {
            var temp = new ClubActivityPosivity();
            temp.setClubname(ca.get(0));
            temp.setActivityname(ca.get(1));
            var activityreactions = preactivityPositivity.get(ca);
            var len = activityreactions.size();
            temp.setPosivitiy(activityreactions.stream().reduce((a,b) -> a+b).get()/(double)len);
            caplist.add(temp);
        }

        return new ResponseEntity<>(caplist,HttpStatus.OK);
    }



//
//    @PreAuthorize("hasAnyRole('ADMIN','CD')")
//    @GetMapping(value = "/club/{cid}/activity/{aid}")
//    public ResponseEntity<?> getReportByClubActivity(@PathVariable Long cid, @PathVariable Long aid,  @RequestParam(value = "from", required = false) String from,
//            @RequestParam(value = "to", required = false) String to){
//        // how a club activity is received
//
//        return null;
//
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','CD')")
//    @GetMapping(value = "/club/{cid}")
//    public ResponseEntity<?> getReportByClub(@PathVariable String cid,  @RequestParam(value = "from", required = false) String from,
//            @RequestParam(value = "to", required = false) String to){
//        // how activities are received in a club
//        // how members in this club are
//
//        return null;
//
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','CD')")
//    @GetMapping(value = "/activity/{aid}")
//    public ResponseEntity<?> getReportByActivity(@PathVariable String aid,  @RequestParam(value = "from", required = false) String from,
//            @RequestParam(value = "to", required = false) String to){
//
//
//        return null;
//
//    }




}
