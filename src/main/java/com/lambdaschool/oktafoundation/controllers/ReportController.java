package com.lambdaschool.oktafoundation.controllers;


import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.views.ClubActivityPositivity;
import com.lambdaschool.oktafoundation.views.ClubActivityReactionCounts;
import com.lambdaschool.oktafoundation.views.MemberPositivity;
import com.lambdaschool.oktafoundation.views.MemberReactionCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReactionRepository reactionRepository;

    // A helper field to map emoji to value
    private HashMap<String,Integer> emojimap;




    /* The endpoints shall serve basic data aggregate of the member reactions
    with respect to club/activity/member.
     */

    // Currently we have the simple feature of calculating the avgs of positivity value of all members
    // and avgs of the received positivity regarding activities in a club.
    // Likewise the counts of all emojis submitted to activities and submitted by members


    /**
     * Helper method to help build the filters for club and date range
     *
     * @param from Search starts from which date
     * @param to Search until which date
     * @param cid What club is we searching within
     * @return An ensemble of the processed filter strings
     */
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
            clubfilter = "and clubid = " + cid + " ";
        }
        ArrayList<String> x = new ArrayList<>();
        x.add(fromdate); x.add(todate); x.add(clubfilter);
        return x;
    }

    /**
     * Helper method to help build the emoji to value map
     *
     * @return A hashmap that turns emoji into value
     */
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

    /**
     * Returns the positivity value (avgs) from all the Members within certain Club over certain date range.
     *
     * @param from Search starts from which date
     * @param to Search until which date
     * @param cid Search range restricted to which club activities
     * @return The averages of positivities value for all members of the given filters.
     */
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


        // Get initial list of all memberReactions within date range
        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'"  + clubfilter, MemberReactions.class);
        @SuppressWarnings("unchecked")
        List<MemberReactions> mrlist = q.getResultList();
        var prememberPositivity = new HashMap<String, ArrayList<Integer>>();
        // init count object for all members which is a map from memberID to a list of value that the member gave out.
        mrlist.forEach(e -> {
            if(prememberPositivity.containsKey(e.getMember().getMemberid())){
                prememberPositivity.get(e.getMember().getMemberid()).add(emojimap.get(e.getReaction().getReactionvalue()));
            } else {
                var templ = new ArrayList<Integer> ();
                templ.add(emojimap.get(e.getReaction().getReactionvalue()));
                prememberPositivity.put(e.getMember().getMemberid(),templ);
            }
        });

        // calculating all member's avg value of the emoji value list into a number
        List<MemberPositivity> mplist = new ArrayList<>();
        var members = new ArrayList<>(prememberPositivity.keySet());
        for (var member: members) {
            var temp = new MemberPositivity();
            temp.setMemberid(member);
            var memberreactions = prememberPositivity.get(member);
            var len = memberreactions.size();
            temp.setPositivity(memberreactions.stream().reduce(Integer::sum).get()/(double)len);
            mplist.add(temp);
        }


        return new ResponseEntity<>(mplist,HttpStatus.OK);
    }

    /**
     * Returns the positivity value (avgs) given to all the Activities within certain Club over certain date range.
     *
     * @param from Search starts from which date
     * @param to Search until which date
     * @param cid Search range restricted to which club activities
     * @return The averages of positivities value for all members of the given filters.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/activities/avgs")
    public ResponseEntity<?> getAvgsByActivities(
        @PathVariable Long cid,
        @RequestParam(value = "from", required = false) String from,
        @RequestParam(value = "to", required = false) String to
    ){
        // This endpoint mirrors the one from above, but to activities instead.
        var filters = initFilters(from,to,cid);
        var fromdate = filters.get(0);
        var todate = filters.get(1);
        var clubfilter = filters.get(2);
        var emojimap = initEmojimap();

        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'" + clubfilter,MemberReactions.class);
        @SuppressWarnings("unchecked")
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
            temp.setPositivity(activityreactions.stream().reduce(Integer::sum).get()/(double)len);
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

    /**
     * Returns the counts of different emojis submitted to all the Activities within certain Club over certain date range.
     *
     * @param from Search starts from which date
     * @param to Search until which date
     * @param cid Search range restricted to which club activities
     * @return The averages of positivities value for all members of the given filters.
     */
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
        @SuppressWarnings("unchecked")
        List<MemberReactions> mrlist = q.getResultList();
        // this map takes in key of [clubname,activityname] and hashes into a hashmap that map emojis with their counts.
        var resmap = new HashMap<List<String>, HashMap<String,Integer>>();
        // iterate over the memberReactions list returned by the query and count occurences of emojis given to certain activities.
        mrlist.forEach(e -> {
            var ckey = new ArrayList<String>();
            ckey.add(e.getClubactivity().getClub().getClubname());
            ckey.add(e.getClubactivity().getActivity().getActivityname());
            if(resmap.containsKey(ckey)){
                var curcount = resmap.get(ckey).get(e.getReaction().getReactionvalue());
                // count increase for this particular emoji
                resmap.get(ckey).put(e.getReaction().getReactionvalue(), curcount+1);
            } else {
                resmap.put(ckey,createNewCountMap());
            }
        });

        // building return object
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


    /**
     * Returns the counts of different emojis submitted by all members within certain Club over certain date range.
     *
     * @param from Search starts from which date
     * @param to Search until which date
     * @param cid Search range restricted to which club activities
     * @return The averages of positivities value for all members of the given filters.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/club/{cid}/members/counts")
    public ResponseEntity<?> getCounts(
            @PathVariable Long cid,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to
    ) {
        // This endpoint mirrors the one above, but counts emojis that member give rather than emojis that activity receives
        var filters = initFilters(from,to,cid);
        var fromdate = filters.get(0);
        var todate = filters.get(1);
        var clubfilter = filters.get(2);
        var emojimap = initEmojimap();

        var q = entityManager.createNativeQuery("select * from memberreactions where created_date >= date'" + fromdate + "'" +" and created_date <= date'" + todate + "'" + clubfilter,MemberReactions.class);
        @SuppressWarnings("unchecked")
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
