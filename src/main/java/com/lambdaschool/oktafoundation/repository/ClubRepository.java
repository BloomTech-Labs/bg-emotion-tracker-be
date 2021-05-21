package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.views.ClubSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The CRUD repository connecting Club to the rest of the application
 */
public interface ClubRepository extends CrudRepository<Club, Long> {
   /**
    * JPA Query to get a summary of the Clubs containing the name and id of each Club
    *
    * @return The summary of all the Clubs
    */
   @Query(
           value = "select clubid,clubname from clubs",
           nativeQuery = true
   )
   List<ClubSummary> getClubsSummary();

   /**
    * Finds a Club with the given name.
    *
    * @param name The name (String) of the Club you seek
    * @return The first Club object with the given name
    */
   Optional<Club> findClubByClubname(String name);
}
