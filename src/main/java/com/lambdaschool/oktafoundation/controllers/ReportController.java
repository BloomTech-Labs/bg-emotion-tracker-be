package com.lambdaschool.oktafoundation.controllers;


import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import com.lambdaschool.oktafoundation.views.ClubActivityPositivity;
import com.lambdaschool.oktafoundation.views.ClubActivityReactionCounts;
import com.lambdaschool.oktafoundation.views.MemberPositivity;
import com.lambdaschool.oktafoundation.views.MemberReactionCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/report")
public class ReportController {


    @Autowired
    private MemberReactionService memberReactionService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReactionRepository reactionRepository;


    private HashMap<String,Integer> emojimap;

    public ReportController(){

    }


    /* The endpoints shall serve data analysis of the member reactions
    with respect to club/activity/member.
     */

    // Currently we have the simple feature of calculating the avgs of positivity value of all members
    // and avgs of the received positivity regarding activities in a club.


    private ArrayList<String> initFilters(String from, String to, Long cid) {
        var fromdate = from;
        var todate = to;
        if(from == null){
            fromdate = "1000-01-01";
        }
        if(to == null){
            todate = "3000-01-01";
        }

        // Whether we restrict the scope to a club
        // if cid = 0; we search all clubs
        var clubfilter = "";
        if (cid.intValue() != 0){
            clubfilter = "and clubid = " + cid.toString()+ " ";
        }
        ArrayList<String> x = new ArrayList<>();
        x.add(fromdate); x.add(todate); x.add(clubfilter);
        return x;
    }

    private HashMap<String,Integer> initEmojimap(){
        var allReactions =  StreamSupport
                .stream(reactionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        emojimap = new HashMap<>();
        emojimap.put("1F601", 2);
        emojimap.put("1F642", 1);
        emojimap.put("1F610", 0);
        emojimap.put("1F641", -1);
        emojimap.put("1F61E", -2);

        for (var i: allReactions){
            if(emojimap.containsKey(i.getReactionvalue())){continue;}
            emojimap.put(i.getReactionvalue(),0);
        }

        return emojimap;
    }


    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/members/avgs")
    public ResponseEntity<?> getAvgsByMembers(
            @PathVariable Long cid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to){

        var filters = initFilters(from,to,cid);
        var fromdate = filters.get(0);
        var todate = filters.get(1);
        var clubfilter = filters.get(2);
        var emojimap = initEmojimap();



        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'"  + clubfilter,MemberReactions.class);
        List<MemberReactions> mrlist = q.getResultList();
        var prememberPositivity = new HashMap<String, ArrayList<Integer>>();
//        System.out.println(mrlist.get(0));
        mrlist.forEach(e -> {
            if(prememberPositivity.containsKey(e.getMember().getMemberid())){
                prememberPositivity.get(e.getMember().getMemberid()).add(emojimap.get(e.getReaction().getReactionvalue()));
            } else {
                var templ = new ArrayList<Integer> ();
                templ.add(emojimap.get(e.getReaction().getReactionvalue()));
                prememberPositivity.put(e.getMember().getMemberid(),templ);
            }
        });

        List<MemberPositivity> mplist = new ArrayList<>();
        var members = new ArrayList<>(prememberPositivity.keySet());
        for (var member: members) {
            var temp = new MemberPositivity();
            temp.setMemberid(member);
            var memberreactions = prememberPositivity.get(member);
            var len = memberreactions.size();
            temp.setPositivity(memberreactions.stream().reduce((a,b) -> a+b).get()/(double)len);
            mplist.add(temp);
        }


        return new ResponseEntity<>(mplist,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/activities/avgs")
    public ResponseEntity<?> getAvgsByActivities(
        @PathVariable Long cid,
        @RequestParam(value = "from", required = false) String from,
        @RequestParam(value = "to", required = false) String to
    ){
        var filters = initFilters(from,to,cid);
        var fromdate = filters.get(0);
        var todate = filters.get(1);
        var clubfilter = filters.get(2);
        var emojimap = initEmojimap();

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

        List<ClubActivityPositivity> caplist = new ArrayList<>();
        var activities = new ArrayList<>(preactivityPositivity.keySet());
        for (var ca: activities) {
            var temp = new ClubActivityPositivity();
            temp.setClubname(ca.get(0));
            temp.setActivityname(ca.get(1));
            var activityreactions = preactivityPositivity.get(ca);
            var len = activityreactions.size();
            temp.setPositivity(activityreactions.stream().reduce((a,b) -> a+b).get()/(double)len);
            caplist.add(temp);
        }

        return new ResponseEntity<>(caplist,HttpStatus.OK);
    }

    private HashMap<String,Integer> createNewCountMap(){
        var templ = new HashMap<String,Integer> ();
        for (var e: emojimap.keySet()){
            templ.put(e, 0);
        }
        return templ;
    }


    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/activities/counts")
    public ResponseEntity<?> getCountsByActivities(
            @PathVariable Long cid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to
    ) {
        var filters = initFilters(from,to,cid);
        var fromdate = filters.get(0);
        var todate = filters.get(1);
        var clubfilter = filters.get(2);
        var emojimap = initEmojimap();

        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'" + clubfilter,MemberReactions.class);
        List<MemberReactions> mrlist = q.getResultList();
        var resmap = new HashMap<List<String>, HashMap<String,Integer>>();
        mrlist.forEach(e -> {
            var ckey = new ArrayList<String>();
            ckey.add(e.getClubactivity().getClub().getClubname());
            ckey.add(e.getClubactivity().getActivity().getActivityname());
            if(resmap.containsKey(ckey)){
                var curcount = resmap.get(ckey).get(e.getReaction().getReactionvalue());
                resmap.get(ckey).put(e.getReaction().getReactionvalue(), curcount+1);
            } else {
                resmap.put(ckey,createNewCountMap());
            }
        });

        var reslis = new ArrayList<ClubActivityReactionCounts>();
        for (var e: resmap.keySet()){
            var temp = new ClubActivityReactionCounts();
            var cname = e.get(0);
            var aname = e.get(1);
            var cakey = new ArrayList<>();
            cakey.add(cname); cakey.add(aname);
            temp.setClubname(cname);
            temp.setActivityname(aname);
            temp.setReactionCounts(resmap.get(cakey));
            reslis.add(temp);
        }


        return new ResponseEntity<>(reslis,HttpStatus.OK);

    }


    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/members/counts")
    public ResponseEntity<?> getCounts(
            @PathVariable Long cid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to
    ) {
        var filters = initFilters(from,to,cid);
        var fromdate = filters.get(0);
        var todate = filters.get(1);
        var clubfilter = filters.get(2);
        var emojimap = initEmojimap();

        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'" + clubfilter,MemberReactions.class);
        List<MemberReactions> mrlist = q.getResultList();
        var resmap = new HashMap<String, HashMap<String,Integer>>();
        mrlist.forEach(e -> {
            var mkey = e.getMember().getMemberid();
            if(resmap.containsKey(mkey)){
                var curcount = resmap.get(mkey).get(e.getReaction().getReactionvalue());
                resmap.get(mkey).put(e.getReaction().getReactionvalue(), curcount+1);
            } else {
                resmap.put(mkey,createNewCountMap());
            }
        });

        var reslis = new ArrayList<MemberReactionCounts>();
        for (var mname: resmap.keySet()){
            var temp = new MemberReactionCounts();
            temp.setMemberid(mname);
            temp.setReactionCounts(resmap.get(mname));
            reslis.add(temp);
        }


        return new ResponseEntity<>(reslis,HttpStatus.OK);

    }



}
