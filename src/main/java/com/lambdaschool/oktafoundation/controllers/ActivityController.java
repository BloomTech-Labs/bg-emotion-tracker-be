package com.lambdaschool.oktafoundation.controllers;

import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.repository.ClubActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.services.ActivityService;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.UserService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

    /**
     * Returns a list of all activities
     * <br>Example: <a href="http://localhost:2019/activities/activities"></a>
     *
     * @return JSON list of all activities with a status of OK
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
     * Returns a single activity based off a activity id number
     * <br>Example: http://localhost:2019/activities/activity/4
     *
     * @param activityid The primary key of the activity you seek
     * @return JSON object of the activity you seek
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
     * Given a complete Activity Object, Super Admin, CD can , create a new Activity record, assign an accompanything club,
     * memberreaction, the members, and reactions.
     * @param newActivity A complete new activity
     *
     * @return A location header with the URI to the newly created activity of CREATED
     *
     * @throws URISyntaxException Exception if something does not work in creating the location header
     *
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
     * Given a complete Activity Object
     * Given the activity id, primary key, is in the Activity table,
     * replace the Activity record, all records associated.
     * <br> Example: <a href="http://localhost:2019/activities/activity/5"></a>
     *
     * @param updateActivity A complete Activity it replace the Activity.
     * @param activityid     The primary key of the activity you wish to replace.
     * @return status of NO_CONTENT
     */
    @PatchMapping(value = "/activity/{activityid}",
    consumes = "application/json")
    public ResponseEntity<?> updateActivity(@RequestBody Activity updateActivity, @PathVariable long activityid)
    {
        activityService.update(updateActivity, activityid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * Deletes a given activity along with associated clubactivities
     * <br>Example: <a href="http://localhost:2019/activities/activity/5"></a>
     *
     * @param activityid the primary key of the activity you wish to delete
     * @return Status of NO_CONTENT
     */




    @DeleteMapping(value = "/activity/{activityid}")
    public ResponseEntity<?> deleteActivityById(@PathVariable long activityid)
    {
        activityService.delete(activityid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(value = "/activity/addclub/{clubid}",consumes = "application/json")
    public ResponseEntity<?> addActivityToClub(@RequestBody Activity activity, @PathVariable long clubid){
        Activity newact;
        if (activity.getActivityid() == 0){
            newact = activityService.save(activity);
        } else {
            newact = activityService.findActivityById(activity.getActivityid());
        }

        Club club = clubRepository.findById(clubid).orElseThrow();
        ClubActivities temp =  new ClubActivities(club,newact);
        club.getActivities().add(temp);
        clubRepository.save(club);;
        return new ResponseEntity<>(HttpStatus.OK);




    }


}
