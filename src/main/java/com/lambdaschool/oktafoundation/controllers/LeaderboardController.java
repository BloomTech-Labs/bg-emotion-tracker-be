package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Reaction;
import com.lambdaschool.oktafoundation.repository.ActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.views.ClubsCheckInOutSummary;
import com.lambdaschool.oktafoundation.views.LeaderBoardData;
import com.lambdaschool.oktafoundation.views.LeaderboardReactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {
    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubMembersRepository clubMembersRepository;

    @Autowired
    ActivityRepository activityRepo;

    @Autowired
    ReactionRepository reactionRepo;

//  Highest Sentiment

//  Most improved
//    @PreAuthorize("hasAnyRole('ADMIN','CD')")
    @GetMapping(value = "/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        //  Grab the response from the sql query
        List<ClubsCheckInOutSummary> rtnList = clubRepository.getClubsCheckInOutSummary();
        List<LeaderBoardData> leaderboard = new ArrayList<>();
        List<LeaderboardReactions> leaderboardReactions = new ArrayList<>();
        // We want to separate and compare the values for checkins and checkouts
        ClubsCheckInOutSummary checkin;
        ClubsCheckInOutSummary checkout;
        long checkinId = 7;/* activityRepo.findActivityByActivityname("Club Checkin").orElseThrow(() -> new ResourceNotFoundException("Activity name Club Checkin not found.")).getActivityid();*/
        long checkoutId = 14; /*activityRepo.findActivityByActivityname("Club Checkout").orElseThrow(() -> new ResourceNotFoundException("Activity name Club Checkout not found.")).getActivityid();*/

        Integer checkinIndex = 0;
        Integer checkoutIndex = 1;
        while(checkinIndex < rtnList.size() - 1) {
        LeaderboardReactions datapointReactions = new LeaderboardReactions();
//            grab the element at checkinIndex, if it is checkin then store it
//            if not increment checkinIndex
            while(rtnList.get(checkinIndex).getActivityid() != checkinId) {
                if(checkinIndex + 1 < rtnList.size()){
                    checkinIndex += 1;
                }
            }
            checkin = rtnList.get(checkinIndex);
//            Once we have checkin, we check if checkoutIndex points to a checkout
//            if not we increment second index
            while(rtnList.get(checkoutIndex).getActivityid() != checkoutId) {
                if(checkoutIndex + 1 < rtnList.size()) {
                    checkoutIndex += 1;
                }
            }
            checkout = rtnList.get(checkoutIndex);
            ClubsCheckInOutSummary finalCheckout = checkout;
            ClubsCheckInOutSummary finalCheckin = checkin;
            Reaction checkoutReaction =  reactionRepo.findReactionByReactionid(checkout.getReactionid()).orElseThrow(() -> new ResourceNotFoundException("reaction id " + finalCheckout.getReactionid() + " Not found" ));
            Reaction checkinReaction =  reactionRepo.findReactionByReactionid(checkin.getReactionid()).orElseThrow(() -> new ResourceNotFoundException("reaction id " + finalCheckin.getReactionid() + " Not found" ));

            AtomicBoolean clubInLeaderboardReactions = new AtomicBoolean(false);
            ClubsCheckInOutSummary finalCheckin1 = checkin;
            // we want to add data point if it isnt in leaderboardReactions,
            // but if it is we want to modify datapointReactions before adding it
//            leaderboardReactions.it.forEach(leaderBoardReactionData -> {
            Integer dataIndex = 0;
            int ind = 0;
            for( LeaderboardReactions leaderBoardReactionData: leaderboardReactions){
                if(leaderBoardReactionData.getClubid() == finalCheckin1.getClubid()){
                    clubInLeaderboardReactions.set(true);
                    dataIndex = ind;
                }
                ind += 1;
            }
//            });
            // get reactionrating for this run to be added to the array
            var reactionRatingDiff = (double) checkoutReaction.getReactionint() - checkinReaction.getReactionint();
            if(clubInLeaderboardReactions.get()) {
                // if the leaderboard reactions is in leaderboardreactoins
                // need to get the right object form leaderboardReactions
                // then add in the new reactionRatingDiff
                leaderboardReactions.get(dataIndex).getMemberReactionRatings().add(reactionRatingDiff);
            } else {
                // create new Leaderboard reaction and add it to leaderboardReactions
                datapointReactions.setClubid(checkin.getClubid());
                ArrayList<Double> newDouble = new ArrayList<>();
                newDouble.add(reactionRatingDiff);
                datapointReactions.setMemberReactionRatings(newDouble);
                leaderboardReactions.add(datapointReactions);
            }

            checkinIndex += 2;
            checkoutIndex = checkinIndex + 1;
        }
        // Now we need to take the leaderboardReactions and turn the memeberReactionRatings into an average
        // then add it to leaderboard
        for(LeaderboardReactions leaderboardReactionsData: leaderboardReactions) {
        LeaderBoardData datapoint = new LeaderBoardData();
            double reactionSum = 0;
            for(double reactionVal: leaderboardReactionsData.getMemberReactionRatings()){
                reactionSum += reactionVal;
            }
            double reactionAvg = reactionSum / leaderboardReactionsData.getMemberReactionRatings().size();

            datapoint.setClubid(leaderboardReactionsData.getClubid());
            datapoint.setClubrating(reactionAvg);
          String clubName = clubRepository.findClubByClubid(leaderboardReactionsData.getClubid()).getClubname();
          datapoint.setClubname(clubName);
          leaderboard.add(datapoint);
        }
//      we can set up leaderboard
//        return new ResponseEntity<>(leaderboardReactions, HttpStatus.OK);
        return new ResponseEntity<>(leaderboard, HttpStatus.OK);
//        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
}
