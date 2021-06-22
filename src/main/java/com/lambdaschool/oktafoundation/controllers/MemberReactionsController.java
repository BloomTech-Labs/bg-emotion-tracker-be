package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.*;
import com.lambdaschool.oktafoundation.services.MemberReactionService;
import com.lambdaschool.oktafoundation.views.SearchPost;
import com.lambdaschool.oktafoundation.views.AlertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

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
    private ClubMembersRepository clubMembersRepository;


    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private EntityManager entityManager;


    /**
     * Returns a list of all MemberReactions.
     * <br>Example: <a href="http://localhost:2019/memberreactions/memberreactions"></a>
     *
     * @return JSON list of all MemberReactions with a status of OK
     */
    @GetMapping(value = "/memberreactions",
            produces = "application/json")
    public ResponseEntity<?> listAllMemberReactions() {
        List<MemberReactions> allMemberReactions = memberReactionService.findAll();
        return new ResponseEntity<>(allMemberReactions, HttpStatus.OK);
    }

    /**
     * Returns the MemberReaction with the given id.
     * <br>Example: http://localhost:2019/memberreactions/memberreaction/7
     *
     * @param id The primary key of the MemberReaction you seek
     * @return JSON object of the MemberReaction you seek
     * @see MemberReactionService#
     */
    @GetMapping(value = "/memberreaction/{id}",
            produces = "application/json")
    public ResponseEntity<?> getMemberReactionById(@PathVariable Long id) {
        MemberReactions mr = memberReactionService.findMemberReactionById(id);
        return new ResponseEntity<>(mr, HttpStatus.OK);
    }

    /**
     * Adds a new MemberReaction with the given memberId, activityId, clubId, and reaction.
     *
     * @param mid The id of the Member whose reaction is being added
     * @param aid The id of the Activity for which the Member gave this Reaction
     * @param cid The id of the Club at which the Member gave this Reaction
     * @param rx  A string representing the Reaction, in the form of the selected emoji's Unicode codepoint
     * @return Status of OK
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @PostMapping(value = "/memberreaction/submit")
    public ResponseEntity<?> addNewReaction(
            @RequestParam(value = "mid") String mid,
            @RequestParam(value = "aid") Long aid,
            @RequestParam(value = "cid") Long cid,
            @RequestParam(value = "rx") String rx
    ) {

        var normallist = new HashMap<String, Integer>();
        normallist.put("1F601", 0);
        normallist.put("1F642", 0);
        normallist.put("1F610", 0);
        normallist.put("1F641", 0);
        normallist.put("1F61E", 0);


        Member member;
        var premember = memberRepository.findMemberByMemberid(mid);
        if (premember.isEmpty()) {
            return new ResponseEntity<>("No such member", HttpStatus.NOT_FOUND);
        } else {
            member = premember.get();
        }


        var preca = clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(
                aid, cid
        );

        ClubActivities ca;
        if (preca.isEmpty()) {
            return new ResponseEntity<>("No such Club Activity", HttpStatus.NOT_FOUND);
        } else {
            ca = preca.get();
        }


        var checkInOut = Pattern.compile("check.?(in|out)$", Pattern.CASE_INSENSITIVE);
        var aname = ca.getActivity().getActivityname();
        if (!checkInOut.matcher(aname).find()) {
            if (!normallist.containsKey(rx)) {
                return new ResponseEntity<>("This emoji can't be used in regular activity", HttpStatus.NOT_ACCEPTABLE);
            }

        }


        clubMembersRepository.save(new ClubMembers(clubRepository.findById(cid).orElseThrow(), member));

        Reaction currentreaction;
        var precurrentreaction = reactionRepository.findReactionByReactionvalue(rx);
        if (precurrentreaction.isEmpty()) {
            return new ResponseEntity<>("No such emoji", HttpStatus.NOT_ACCEPTABLE);
        } else {
            currentreaction = precurrentreaction.get();
        }

        MemberReactions temp = new MemberReactions(member, currentreaction, ca);


        memberReactionRepository.save(temp);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Helper method to help build the native SQL search query string
     *
     * @param from    Search starts from which date
     * @param to      Search until which date
     * @param ca      Search range restricted to which club activities
     * @param members Search range restricted to which members
     * @return The built partial query string
     */
    private String searchQueryBuilder(LocalDate from, LocalDate to, List<ClubActivities> ca, List<Member> members) {
        var res = new StringBuilder();

        if (from != null || to != null) {
            if (from != null && to != null) {
                res.append(" created_date >= date '").append(from).append("'");
                res.append(" and ");
                res.append(" created_date <= date '").append(to).append("' ");
            } else if (from != null) {
                res.append(" created_date >= date '").append(from).append("' ");
            } else {
                res.append(" created_date <= date '").append(to).append("' ");
            }
        }
        if (from == null && to == null) {
            res.append(" true ");
        }

        // adding filters for members
        if (members != null && members.size() > 0) {

            res.append(" and member_table_id in (");
            for (var e : members) {
                res.append(e.getMember_table_id()).append(",");
            }
            res.deleteCharAt(res.length() - 1);
            res.append(")");
        }

        // adding filters for club activities
        if (ca != null && ca.size() > 0) {
            res.append(" and (clubid,activityid) in (");
            for (var e : ca) {
                res.append("(").append(e.getClub().getClubid()).append(",").append(e.getActivity().getActivityid()).append(")").append(",");
            }
            res.deleteCharAt(res.length() - 1);
            res.append(")");
        }


        System.out.println(res);

        return res.toString();
    }

    /**
     * Given start and end dates, returns all the relevant reactions from the Members (or to the ClubActivities) specified in the request body.
     *
     * @param from The earliest date by which to filter reactions
     * @param to   The latest date by which to filter reactions
     * @param sp   Other data such as ClubActivities and/or Members by which to filter the reactions
     * @return A list of the relevant MemberReactions
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @PostMapping(value = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestBody SearchPost sp
    ) throws Exception {

        List<ClubActivities> calist = new ArrayList<>();
        List<Member> mlist = new ArrayList<>();

        if (sp.getActivities() == null) {
            calist = null;
        } else {
            for (var e : sp.getActivities()) {
                calist.add(clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(e.getActivityid(), e.getClubid()).orElseThrow());
            }
        }
        if (sp.getMembers() == null) {
            mlist = null;
        } else {
            for (var e : sp.getMembers()) {
                mlist.add(memberRepository.findMemberByMemberid(e.getMemberid()).orElseThrow());
            }
        }

        //if no body presented, then it means we just search against all memberreactions within certain date range
        LocalDate fromstr = null;
        LocalDate tostr = null;
        if (from != null) {
            fromstr = LocalDate.parse(from);
        }
        if (to != null) {
            tostr = LocalDate.parse(to);
        }

        //Use the partial querystring generated by the query builder
        Query q = entityManager.createNativeQuery("select * from memberreactions where " + searchQueryBuilder(fromstr, tostr, calist, mlist), MemberReactions.class);

        return new ResponseEntity<>(q.getResultList(), HttpStatus.OK);
    }

    /**
    Populates the alert list with memberreactions that are not resolved and where activityid=7 (check-in) or activityid=14 (checkout)
     * <br>Example: <a href="http://localhost:2019/memberreactions/alert"></a>
     **/
//    @PreAuthorize("hasAnyRole('ADMIN','CD','YDP')")
    @GetMapping(value = "/alert", produces = "application/json")
    public ResponseEntity<?> getAlerts() {
        List<AlertData> rtnList = memberReactionService.getMemberReactionsByReactionresolved();
//        List<AlertsList> alerts = new ArrayList<>();

        return new ResponseEntity<>(rtnList, HttpStatus.OK);

    }

    /**
     Changes the value in member reactions for {memberreactionid} to the opposite of the pre-existing value.
     The json object submitted is empty ({ }).
     * <br>Example: <a href="http://localhost:2019/memberreactions/update/{memberreactionid}"></a>
     **/
    @PutMapping(value = "/update/{memberreactionid}")
    public ResponseEntity<?> updateAlerts(@RequestBody MemberReactions updateMr,
                                          @PathVariable long memberreactionid)
    {
        memberReactionService.update(memberreactionid, updateMr);
        return new ResponseEntity<>(updateMr, HttpStatus.OK);
    }

}
