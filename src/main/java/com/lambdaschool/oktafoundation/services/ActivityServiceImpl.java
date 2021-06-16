package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements ActivityService Interface
 */
@Transactional
@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService{

    /**
     * Connects this service to the Activity table.
     */
    @Autowired
    private ActivityRepository activityRepo;
    /**
     * Connects this service to the Club table.
     */
    @Autowired
    private ClubRepository clubRepo;

    @Override
    public List<Activity> findAll() {
        List<Activity> activityList = new ArrayList<>();

        activityRepo.findAll()
                .iterator()
                .forEachRemaining(activityList::add);
        return activityList;
    }

    @Override
    public Activity findActivityById(Long activityid) {
        return activityRepo.findById(activityid)
                .orElseThrow(() -> new ResourceNotFoundException("Activity id" + activityid + "not found."));
    }


    @Override
    public Activity findActivityByName(String name) {
        return activityRepo.findActivityByActivitynameLike(name).orElseThrow(() -> new ResourceNotFoundException("Activity name " + name + " not found."));
    }



    @Override
    public Activity save(Activity newActivity) {
        Activity saveActivity = new Activity();

        if(newActivity.getActivityid() != 0)
        {
            activityRepo.findById(newActivity.getActivityid())
                    .orElseThrow(() -> new ResourceNotFoundException("Activity id" + newActivity.getActivityid() + "not found."));
            saveActivity.setActivityid(newActivity.getActivityid());
        }
//      set Fields
        saveActivity.setActivityname(newActivity.getActivityname());
//      set Relationships
        newActivity.getClubs()
                .clear();
        for(ClubActivities ca: newActivity.getClubs())
        {
            Club newClub = new Club();
            newClub.setClubname(ca.getClub().getClubname());

            ClubActivities newclubActivities = new ClubActivities();
            newclubActivities.setActivity(saveActivity);
            newclubActivities.setClub(newClub);
            newclubActivities.getReactions().clear();
            for (MemberReactions mr: ca.getReactions())
            {
                Member newMember = new Member();
                newMember.setMemberid(mr.getMember().getMemberid());

                Reaction newReaction = new Reaction();
                newReaction.setReactionvalue(mr.getReaction().getReactionvalue());

                MemberReactions newMemberReaction = new MemberReactions();
                newMemberReaction.setMember(newMember);
                newMemberReaction.setReaction(newReaction);
                newMemberReaction.setClubactivity(newclubActivities);

                newclubActivities.getReactions().add(newMemberReaction);
            }
            newActivity.getClubs().add(newclubActivities);
        }
        return activityRepo.save(newActivity);
    }

    @Override
    public Activity update(Activity updateActivity, long activityid) {

        Activity updateCurrentActivity = activityRepo.findById(activityid)
                .orElseThrow(() -> new ResourceNotFoundException("Activity id " + activityid + "not found."));

//      set fields
        if (updateActivity.getActivityname() != null)
        {
            updateCurrentActivity.setActivityname(updateActivity.getActivityname());
        }
//      set relationships
        if(updateActivity.getClubs()
                .size() >0)
        {
            updateCurrentActivity.getClubs()
                    .clear();
            for(ClubActivities ca: updateActivity.getClubs())
            {
                Club newClub = clubRepo.findById(ca.getClub().getClubid())
                        .orElseThrow(() -> new EntityNotFoundException("Club id" + ca.getClub().getClubid() + "not found."));

                updateCurrentActivity.getClubs().add(new ClubActivities(newClub, updateCurrentActivity));
            }
        }
        return activityRepo.save(updateCurrentActivity);

    }

    @Override
    public void delete(long activityid) {
        activityRepo.findById(activityid)
                .orElseThrow(() -> new ResourceNotFoundException("Activity id" + activityid + "not found!"));
        activityRepo.deleteById(activityid);
    }


    @Transactional
    @Override
    public void deleteAll()
    {
        activityRepo.deleteAll();
    }
}
