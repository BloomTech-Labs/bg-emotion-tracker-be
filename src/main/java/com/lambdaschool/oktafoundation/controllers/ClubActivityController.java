package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.services.*;
import com.lambdaschool.oktafoundation.views.*;
import io.swagger.annotations.ApiOperation;
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
    private ClubService clubService;

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ReactionService reactionService;
  
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
    @PreAuthorize("hasAnyRole('ADMIN', 'CD')")
    @GetMapping(value = "/feedback",
        produces = "application/json")
    public ResponseEntity<?> getClubActivityFeedback()
    {
        long checkinid = activityService.findActivityByName("Club Checkin").getActivityid();
        long checkoutid = activityService.findActivityByName("Club Checkout").getActivityid();
        /**  List to hold all the data from the custom sql query  */
        List<ClubActivityFeedbackData> memberReactionsNotCheckInOut =  memberReactionRepository.getMemberReactionsNotCheckInOut(checkinid, checkoutid);
        /**  List to hold the intermediate data for calculating ratings
        *   This will hold all of the reaction values
        *   */
        List<ClubActivityFeedbackReactions> feedbackReactionsList = new ArrayList<>();
        /**  List to hold the final return data including activity ratings  */
        List<ClubActivityFeedback> rtnList = new ArrayList<>();

        for(ClubActivityFeedbackData clubActivityFeedback: memberReactionsNotCheckInOut) {
            /** boolean used to determine whether or not the club is alread in feedbackReactionsList*/
            boolean clubInfeedbackReactionsList = false;
            for(ClubActivityFeedbackReactions feedbackReaction: feedbackReactionsList) {
                /** If the club is in feedbackReactionsList then check to see if the current looked activity is associated with that club */
                if(feedbackReaction.getClubid() == clubActivityFeedback.getClubid()) {
                    clubInfeedbackReactionsList = true;

                    List<ActivityReaction> feedbackReactionActivityReactions = feedbackReaction.getActivityreactions();
                    boolean activityIdFound = false;
                    for(ActivityReaction ar: feedbackReactionActivityReactions) {
                        /** This is the case where the club is found and activity is found.
                         *  Here we just add the reactionint for the reaction given */
                        if(ar.getActivityid() == clubActivityFeedback.getActivityid()) {
                            activityIdFound = true;
                            Double reactionIntval = reactionService.findReactionById(clubActivityFeedback.getReactionid()).getReactionint();
                            ar.getReactionints().add(reactionIntval);
                        }
                    }
                    /** The case were the club is found but not the activity.
                     *  Here we need to add the activity and first reactionint */
                    if(!activityIdFound){
                        ActivityReaction newAR = new ActivityReaction();
                        newAR.setActivityid(clubActivityFeedback.getActivityid());
                        Double reactionIntval = reactionService.findReactionById(clubActivityFeedback.getReactionid()).getReactionint();
                        List<Double> newIntegerList = new ArrayList<>();
                        newIntegerList.add(reactionIntval);
                        newAR.setReactionints(newIntegerList);
                        // add newAR to the club
                        feedbackReaction.getActivityreactions().add(newAR);
                    }
                }
            }
            /** The case where the club is not found
             *  Here we need to add the club, and the first activity and first reactionint*/
            if(!clubInfeedbackReactionsList) {
                ClubActivityFeedbackReactions newClubAct = new ClubActivityFeedbackReactions();
                ActivityReaction newActivityReaction = new ActivityReaction();

                List<Double> newIntList = new ArrayList<>();
                newIntList.add(reactionService.findReactionById(clubActivityFeedback.getReactionid()).getReactionint());
                List<ActivityReaction> newActivityReactionList = new ArrayList<>();
                newActivityReactionList.add(newActivityReaction);

                newActivityReaction.setActivityid(clubActivityFeedback.getActivityid());
                newActivityReaction.setReactionints(newIntList);

                newClubAct.setClubid(clubActivityFeedback.getClubid());
                newClubAct.setActivityreactions(newActivityReactionList);
                feedbackReactionsList.add(newClubAct);
            }
        }

            /** We now have a list of all the clubs and their activities. Under all the activities we have a list of all the reactions to that activity.
             * Here we need to average the reactions and store it in the rtnList */

            for(ClubActivityFeedbackReactions feedbackReactions: feedbackReactionsList){
                ClubActivityFeedback newClubActivityFeedback = new ClubActivityFeedback();

                newClubActivityFeedback.setClubid(feedbackReactions.getClubid());
                newClubActivityFeedback.setClubname(clubService.findClubById(feedbackReactions.getClubid()).getClubname());
                List<ActivityReactionRating> newRatingList = new ArrayList<>();

                for(ActivityReaction activityReaction: feedbackReactions.getActivityreactions()) {
                    ActivityReactionRating newActivityReactionRating = new ActivityReactionRating();
                    newActivityReactionRating.setActivityid(activityReaction.getActivityid());
                    newActivityReactionRating.setActivityname(activityService.findActivityById(activityReaction.getActivityid()).getActivityname());
                    double reactionSum = 0.0;
                    for (Double val: activityReaction.getReactionints()) {
                        reactionSum += (double) val;
                    }
                    double reactionAvg =  reactionSum / (double) activityReaction.getReactionints().size();
                    newActivityReactionRating.setActivityrating(reactionAvg);
                    newRatingList.add(newActivityReactionRating);

                }
                newClubActivityFeedback.setActivityReactionRatings(newRatingList);
                rtnList.add(newClubActivityFeedback);
            }

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }



}
