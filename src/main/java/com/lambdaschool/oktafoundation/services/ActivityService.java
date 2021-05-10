package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Activity;

import java.util.List;
/**
 * The Service that works with Activity Model.
 */
public interface ActivityService {
    /**
     * Returns a list of all the Activities
     *
     * @return List of Activities. If no activities, empty list.
     */
    List<Activity> findAll();
    /**
     * Returns the activity with the given primary key.
     *
     * @param activityid The primary key (long) of the activity you seek.
     * @return The given Activity or throws an exception if not found.
     */
    Activity findActivityById(Long activityid);

    /**
     * Given a complete activity object, saves that activity object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param newActivity the activity object to be saved
     * @return the saved activity object including any automatically generated fields
     */
    Activity save(Activity newActivity);
    /**
     * Updates the provided fields in the activity record referenced by the primary key.
     * @param updateActivity just the activity fields to be updated.
     * @param activityid  The primary key (long) of the activity to update
     * @return the complete activity object that got updated
     */
    Activity update(Activity updateActivity, long activityid);
    /**
     * Deletes the activity by activityid record from the database
     */
    void delete(long activityid);
}
