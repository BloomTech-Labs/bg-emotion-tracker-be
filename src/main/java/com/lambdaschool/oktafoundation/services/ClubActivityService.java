package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubActivities;

import java.util.List;

/**
 * The Service that works with ClubActivity Model.
 */
public interface ClubActivityService {
    /**
     * Returns the ClubActivity with the given primary key.
     *
     * @param clubactivityid The primary key (long) of the clubactivity you seek
     * @return The given ClubActivity or throws an exception if not found
     */
    ClubActivities findClubActivityById(Long clubactivityid);

    /**
     * Returns a list of all the ClubActivities.
     *
     * @return List of ClubActivities; if no ClubActivities, empty list
     */
    List<ClubActivities> findAll();
}
