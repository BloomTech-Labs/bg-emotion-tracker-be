package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubActivities;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting ClubActivity to the rest of the application
 */
public interface ClubActivityRepository extends CrudRepository<ClubActivities, Long> {

   /**
    * Finds an Club-Activity pair by their primary keys
    *
    * @param cid The primary key of the Club
    * @param aid The primary key of the Activity
    * @return The ClubActivity object, if exists.
    */
   Optional<ClubActivities> getClubActivitiesByActivity_ActivityidAndClub_Clubid(Long aid, Long cid);

}
