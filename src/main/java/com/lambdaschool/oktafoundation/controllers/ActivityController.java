package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.repository.ClubActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.services.ActivityService;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.UserService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.invoke.ConstantBootstraps;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * The entry point for clients to access activity data
 */
@RestController
@RequestMapping("/activities")
public class ActivityController {
    /**
     * Using the Activity service to process activity data
     */
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubActivityRepository clubActivityRepository;

    @Autowired
    private MemberReactionRepository memberReactionRepository;

    /**
     * Returns a list of all Activities.
     * <br>Example: <a href="http://localhost:2019/activities/activities"></a>
     *
     * @return JSON list of all Activities with a status of OK
     * @see ActivityService#findAll() ActivityService.findAll()
     */
    @GetMapping(value = "/activities",
    produces = "application/json")
    public ResponseEntity<?> listAllActivities()
    {
        List<Activity> allActivities = activityService.findAll();
        return new ResponseEntity<>(allActivities, HttpStatus.OK);
    }

    /**
     * Returns the Activity with the given id.
     * <br>Example: http://localhost:2019/activities/activity/4
     *
     * @param activityid The primary key of the Activity you seek
     * @return JSON object of the Activity you seek
     * @see ActivityService#findActivityById(Long)  ActivityService.findActivityById(long)
     */
    @GetMapping(value = "/activity/{activityid}",
    produces = "application/json")
    public ResponseEntity<?> getActivityById(@PathVariable Long activityid)
    {
        Activity a = activityService.findActivityById(activityid);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    /**
     * Given a complete Activity object, creates a new Activity, assigning it an accompanying Club.
     *
     * @param newActivity A complete new Activity
     * @return A location header with the URI to the newly created Activity and a status of CREATED
     * @throws URISyntaxException Exception if something does not work in creating the location header
     * @see ActivityService#save(Activity) ActivityService.save(newActivity)
     */
    @PostMapping(value = "/activity",
    consumes = "application/json")
    public ResponseEntity<?> addNewActivity(
            @Valid @RequestBody Activity newActivity) throws URISyntaxException
    {
        newActivity.setActivityid(0);
        newActivity = activityService.save(newActivity);

//        Location Header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newActivityURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newActivity.getActivityid())
                .toUri();
        responseHeaders.setLocation(newActivityURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    /**
     * Updates the Activity record with the given id using the provided data.
     * <br> Example: <a href="http://localhost:2019/activities/activity/5"></a>
     *
     * @param updateActivity An object containing values for just the fields being updated, all other fields left NULL
     * @param activityid     The primary key of the Activity you wish to replace
     * @return status of OK
     */
    @PatchMapping(value = "/activity/{activityid}",
    consumes = "application/json")
    public ResponseEntity<?> updateActivity(@RequestBody Activity updateActivity, @PathVariable long activityid)
    {
        activityService.update(updateActivity, activityid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes the Activity with the given id along with any associated ClubActivities.
     * <br>Example: <a href="http://localhost:2019/activities/activity/5"></a>
     *
     * @param activityid The primary key of the Activity you wish to delete
     * @return Status of NO_CONTENT
     */
    @DeleteMapping(value = "/activity/{activityid}")
    public ResponseEntity<?> deleteActivityById(@PathVariable long activityid)
    {
        activityService.delete(activityid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Given a flexible activity object and a clubId, adds the activity to the Club with the given id.
     * If the activity is given by name and does not exist, it would get created
     * @param activity An activity to add to an existing Club
     * @param clubid The id of the club to which the Activity should be added
     */
    @PostMapping(value = "/activity/addclub/{clubid}",consumes = "application/json")
    public ResponseEntity<?> addActivityToClub(@RequestBody Activity activity, @PathVariable long clubid){
        Activity newact;

        if (activity.getActivityid() == 0){
            try {
                newact = activityService.findActivityByName(activity.getActivityname());
            } catch (Exception e) {
                newact = activityService.save(activity);
            }
        } else {
            newact = activityService.findActivityById(activity.getActivityid());
        }

        Club club = clubRepository.findById(clubid).orElseThrow();
        ClubActivities temp =  new ClubActivities(club,newact);
        club.getActivities().add(temp);
        clubRepository.save(club);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Given a flexible activity object and a clubId, remove the activity to the Club with the given id.
     *
     * @param activity A activity that exists in a club
     * @param clubid The id of the club to which the Activity should removed from
     */
    @PostMapping(value = "/activity/removefrom/{clubid}",consumes = "application/json")
    public ResponseEntity<?> removeActivityFromClub(@RequestBody Activity activity, @PathVariable long clubid){
        ClubActivities ca;

        if (activity.getActivityid() == 0){
            // no id provided, try to find by name
            try {
                var temp = activityService.findActivityByName(activity.getActivityname());
                ca = clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(temp.getActivityid(),clubid).orElseThrow();
                memberReactionRepository.getMemberReactionsByClubactivity_ClubAndClubactivity_Activity(ca.getClub(),ca.getActivity()).forEach(i -> memberReactionRepository.delete(i));
                clubActivityRepository.delete(ca);

            } catch (Exception e) {
                return new ResponseEntity<>("No such club activity", HttpStatus.NOT_MODIFIED);
            }
        } else {
            var temp = clubActivityRepository.getClubActivitiesByActivity_ActivityidAndClub_Clubid(activity.getActivityid(),clubid);
            if (temp.isPresent()){
                ca = temp.get();
                memberReactionRepository.getMemberReactionsByClubactivity_ClubAndClubactivity_Activity(ca.getClub(),ca.getActivity()).forEach(i -> memberReactionRepository.delete(i));
                clubActivityRepository.delete(ca);
            } else {
                return new ResponseEntity<>("No such club activity", HttpStatus.NOT_MODIFIED);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}