package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.services.ActivityService;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import com.lambdaschool.oktafoundation.views.ClubActivityFeedback;
import com.lambdaschool.oktafoundation.views.ClubActivityFeedbackData;
import com.lambdaschool.oktafoundation.views.ClubActivityFeedbackReactions;
import io.swagger.annotations.ApiOperation;
import com.lambdaschool.oktafoundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 * The entry point for clients to access clubactivities data
 */
@RestController
@RequestMapping("/clubactivities")
public class ClubActivityController {
    /**
     * Using the ClubActivities service to process user data
     */
    @Autowired
    private ClubActivityService clubActivityService;

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    @Autowired
    private ActivityService activityService;
  
    /**
     * Returns a list of all clubactivities
     * <br>Example: <a href="http://localhost:2019/clubactivities/clubactivities"></a>
     *
     * @return JSON list of all clubactivities with a status of OK
     */
    @ApiOperation(value = "Returns all club activities",
          response = ClubActivities.class,
          responseContainer = "List")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubactivities",
    produces = "application/json")
    public ResponseEntity<?> listAllClubActivities()
    {
        List<ClubActivities> allClubActivities = clubActivityService.findAll();
        return new ResponseEntity<>(allClubActivities, HttpStatus.OK);
    }
    /**
     * Returns a single clubactivity based off a clubactivity id number
     * <br>Example: http://localhost:2019/clubactivities/clubactivity/7
     *
     * @param clubactivityid The primary key of the clubactivity you seek
     * @return JSON object of the clubactivity you seek
     * @see ClubActivityService#findClubActivityById(Long)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/clubactivity/{clubactivityid}",
        produces = "application/json")
    public ResponseEntity<?> getClubActivityById(@PathVariable Long clubactivityid)
    {
        ClubActivities ca = clubActivityService.findClubActivityById(clubactivityid);
        return new ResponseEntity<>(ca,
                HttpStatus.OK);
    }
//    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @GetMapping(value = "/feedback",
        produces = "application/json")
    public ResponseEntity<?> getClubActivityFeedback()
    {
        long checkinid = activityService.findActivityByName("Club Checkin").getActivityid();
        long checkoutid = activityService.findActivityByName("Club Checkout").getActivityid();
        List<ClubActivityFeedbackData> memberReactionsNotCheckInOut =  memberReactionRepository.getMemberReactionsNotCheckInOut(checkinid, checkoutid);

        List<ClubActivityFeedbackReactions> feedbackReactionsList = new ArrayList<>();
        List<ClubActivityFeedback> rtnList = new ArrayList<>();

        for(ClubActivityFeedbackData clubActivityFeedback: memberReactionsNotCheckInOut) {
            boolean clubInfeedbackReactionsList = false;
//            check if clubActivityFeedback.getClubid() is in feedbackReactionsList
            for(ClubActivityFeedbackReactions feedbackReaction: feedbackReactionsList) {
                if(feedbackReaction.getClubid() == clubActivityFeedback.getClubid()) {
                    clubInfeedbackReactionsList = true;
//                    feedbackReaction
                }
            }

//                check if clubActivityFeedback.getActivityid() is in feedbackreactionsList.get(clubActivityFeedback.getClubid())
//                    if not add it
//                             as well as the reactionint
//                if not add it
//                    as well as the activity id and reacationin

        }

        return new ResponseEntity<>(rtnList,
                HttpStatus.OK);
    }



}
