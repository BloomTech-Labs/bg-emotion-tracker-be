package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubActivities;

import java.util.List;

public interface ClubActivityService {

    ClubActivities findClubActivityById(Long clubactivityid);

    List<ClubActivities> findAll();
}
