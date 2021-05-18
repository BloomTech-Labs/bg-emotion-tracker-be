package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.ClubActivities;
import com.lambdaschool.oktafoundation.views.ClubSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends CrudRepository<Club, Long> {


   @Query(
           value = "select clubid,clubname from clubs",
           nativeQuery = true
   )
   List<ClubSummary> getClubsSummary();


   Optional<Club> findClubByClubname(String name);


}
