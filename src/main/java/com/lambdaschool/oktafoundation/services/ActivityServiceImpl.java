package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "activityService")
public class ActivityServiceImpl implements ActivityService{
    @Autowired
    private ActivityRepository activityRepo;
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
}
