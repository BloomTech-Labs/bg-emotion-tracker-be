package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Activity;

import java.util.List;

public interface ActivityService {

    List<Activity> findAll();

    Activity findActivityById(Long activityid);

    Activity save(Activity newActivity);

    void update(Activity updateActivity, long activityid);

    void delete(long activityid);
}
