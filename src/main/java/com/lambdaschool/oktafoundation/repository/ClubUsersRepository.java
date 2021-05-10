package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubUsers;
import org.springframework.data.repository.CrudRepository;
/**
 * The CRUD repository connecting ClubUsers to the rest of the application
 */
public interface ClubUsersRepository extends CrudRepository<ClubUsers, Long> {
}
