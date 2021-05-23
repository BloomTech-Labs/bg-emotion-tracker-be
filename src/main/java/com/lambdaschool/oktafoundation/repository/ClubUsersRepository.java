package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubUsers;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting ClubUsers to the rest of the application
 */
public interface ClubUsersRepository extends CrudRepository<ClubUsers, Long> {

   /**
    * Finds an Club-User pair by their primary keys
    *
    * @param clubid The primary key of the Club
    * @param userid The primary key of the User
    * @return The ClubUser object, if exists.
    */
   Optional<ClubUsers> findClubUsersByClub_ClubidAndUser_Userid(long clubid, long userid);

}
