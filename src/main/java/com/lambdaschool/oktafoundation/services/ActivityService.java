package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Activity;

import java.util.List;

public interface ActivityService {
    List<Activity> findAll();

    Activity findActivityById(Long activityid);
}
