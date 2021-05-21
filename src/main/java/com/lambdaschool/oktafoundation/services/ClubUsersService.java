package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.ClubUsers;

import java.util.List;

/**
 * The Service that works with ClubUsers Model.
 */
public interface ClubUsersService {
    /**
     * Returns a list of all the ClubUsers.
     *
     * @return List of ClubUsers; if no ClubUsers, empty list
     */
    List<ClubUsers> findAll();

    /**
     * Returns the ClubUser with the given primary key.
     *
     * @param clubuserid The primary key (long) of the ClubUser you seek
     * @return The given ClubUser or throws an exception if not found
     */
    ClubUsers findClubUserById(Long clubuserid);
}
