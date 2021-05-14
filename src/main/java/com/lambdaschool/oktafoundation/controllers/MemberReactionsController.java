package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.services.ActivityService;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import com.lambdaschool.oktafoundation.views.SearchPost;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private EntityManager entityManager;


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

    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @PostMapping(value = "/memberreaction/submit")
    public ResponseEntity<?> addNewReaction(
            @RequestParam(value = "mid") String mid,
            @RequestParam(value = "aid") Long aid,
            @RequestParam(value = "cid") Long cid,
            @RequestParam(value = "rx") String rx
    ) {

        var member = memberRepository.findMemberByMemberid(mid).orElseThrow();
        var ca = clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(
                aid,cid
        ).orElseThrow();
        Reaction currentreaction;
        currentreaction= reactionRepository.findReactionByReactionvalue(rx).orElseThrow();
        // we no longer create any reaction, if the reaction is not in our DB, throw.

        MemberReactions temp = new MemberReactions(member,currentreaction,true, ca);

        memberReactionRepository.save(temp);


        return new ResponseEntity<>(HttpStatus.OK);

    }

    private String searchQueryBuilder(LocalDate from, LocalDate to, List<ClubActivities> ca, List<Member> members){
        var res = new StringBuilder();

        if(from != null || to != null){
            if (from != null && to != null){
                res.append(" created_date >= date '").append(from).append("'");
                res.append(" and ");
                res.append(" created_date <= date '").append(to).append("' ");
            }
            else if (from != null) {
                res.append(" created_date >= date '").append(from).append("' ");
            }
            else {
                res.append(" created_date <= date '").append(to).append("' ");
            }
        }
        if(from == null && to == null){
            res.append(" true ");
        }

        // We enforce only two modes of searching
        // because this is the only way we can plot those.
        // search by clubactivity (1 clubactivity)
        // then any number of members
        if (ca!=null && ca.size()<=1) {
            if(ca.size()==0){
                if (members!=null){
                    res.append(" and member_table_id in (");
                    for (var e: members) {
                        res.append(e.getMember_table_id()).append(",");
                    }
                    res.deleteCharAt(res.length()-1);
                    res.append(")");
                }
            } else {
                res.append(" and cast((cast(clubid as varchar(255))||(cast(activityid as varchar(255))))as bigint) =").append(ca.get(0).getClub().getClubid()).append(ca.get(0).getActivity().getActivityid());
                if (members!=null){
                    res.append(" and member_table_id in (");
                    for (var e: members) {
                        res.append(e.getMember_table_id()).append(",");
                    }
                    res.deleteCharAt(res.length()-1);
                    res.append(")");
                }
            }

            // search by member (1 member)
            // then any number of clubactivities
        } else if (members.size() <=1 ) {
            if(members.size()==0){
                if (ca != null) {
                    res.append(" and cast((cast(clubid as varchar(255))||(cast(activityid as varchar(255))))as bigint) in (");
                    for (var e: ca){
                        res.append(e.getClub().getClubid()).append(e.getActivity().getActivityid()).append(",");
                    }
                    res.deleteCharAt(res.length()-1);
                    res.append(")");
                }

            } else {
                res.append(" and member_table_id=").append(members.get(0).getMember_table_id());
                if (ca != null) {
                    res.append(" and cast((cast(clubid as varchar(255))||(cast(activityid as varchar(255))))as bigint) in (");
                    for (var e: ca){
                        res.append(e.getClub().getClubid()).append(e.getActivity().getActivityid()).append(",");
                    }
                    res.deleteCharAt(res.length()-1);
                    res.append(")");
                }
            }

        } else {
            return "true";
        }

        System.out.println(res.toString());


        return res.toString();
    }



    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @PostMapping(value = "/search")
    public ResponseEntity<?> getReport(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to" , required = false) String to,
            @RequestBody SearchPost sp
    ) throws Exception{

        List<ClubActivities> calist = new ArrayList<>();
        List<Member> mlist = new ArrayList<>();

        for (var e: sp.getActivities()){
            calist.add(clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(e.getActivityid(),e.getClubid()).orElseThrow());
        }

        for (var e: sp.getMembers()){
            mlist.add(memberRepository.findMemberByMemberid(e.getMemberid()).orElseThrow());
        }

        //if no body presented, then it means we just search against all memberreactions within certain date range



        LocalDate fromstr = null;
        LocalDate tostr = null;
        if (from != null){
            fromstr = LocalDate.parse(from);
        }
        if (to != null){
            tostr = LocalDate.parse(to);
        }


        Query q = entityManager.createNativeQuery("select * from memberreactions where " + searchQueryBuilder(fromstr,tostr, calist, mlist),MemberReactions.class);




        return new ResponseEntity<>(q.getResultList(),HttpStatus.OK);
    }





}
