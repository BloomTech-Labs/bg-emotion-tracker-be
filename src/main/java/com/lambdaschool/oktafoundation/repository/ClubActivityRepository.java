package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubActivities;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting ClubActivity to the rest of the application
 */
public interface ClubActivityRepository extends CrudRepository<ClubActivities, Long> {

   Optional<ClubActivities> getClubActivitiesByActivity_ActivityidAndClub_Clubid(Long aid, Long cid);

}
