package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubUsers;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting ClubUsers to the rest of the application
 */
public interface ClubUsersRepository extends CrudRepository<ClubUsers, Long> {

   Optional<ClubUsers> findClubUsersByClub_ClubidAndUser_Userid(long clubid, long userid);

}
