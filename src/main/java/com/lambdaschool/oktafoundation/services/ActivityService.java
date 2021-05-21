package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Activity;

import java.util.List;

/**
 * The Service that works with Activity Model.
 */
public interface ActivityService {
    /**
     * Returns a list of all the Activities.
     *
     * @return List of Activities; if no Activities, empty list
     */
    List<Activity> findAll();

    /**
     * Returns the Activity with the given primary key.
     *
     * @param activityid The primary key (long) of the Activity you seek.
     * @return The given Activity or throws an exception if not found.
     */
    Activity findActivityById(Long activityid);

    /**
     * Returns the Activity with the given name.
     *
     * @param name The name (String) of the Activity you seek
     * @return The Activity with the given name or throws an exception if not found
     */
    Activity findActivityByName(String name);

    /**
     * Given a complete Activity object, saves that Activity object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param newActivity The Activity object to be saved
     * @return The saved Activity object including any automatically generated fields
     */
    Activity save(Activity newActivity);

    /**
     * Updates the provided fields in the Activity record referenced by the primary key.
     *
     * @param updateActivity An object containing just the Activity fields to be updated
     * @param activityid  The primary key (long) of the Activity to update
     * @return The complete Activity object that was updated
     */
    Activity update(Activity updateActivity, long activityid);

    /**
     * Deletes the Activity with the given activityId from the database.
     */
    void delete(long activityid);

    /**
     * Deletes all Activities and their associated records from the database.
     */
    void deleteAll();
}
