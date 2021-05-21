package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The CRUD repository connecting User to the rest of the application
 */
public interface UserRepository
    extends CrudRepository<User, Long>
{
    /**
     * Finds a User with the given username.
     *
     * @param username The name (String) of the User you seek
     * @return The first User object with the name you seek
     */
    User findByUsername(String username);

    /**
     * Find all Users whose name contains a given substring ignoring case.
     *
     * @param name The substring of the name (String) you seek
     * @return List of Users whose name contain the given substring, ignoring case
     */
    List<User> findByUsernameContainingIgnoreCase(String name);
}
