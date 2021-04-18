package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Club;

import java.util.List;

public interface ClubService {

    /**
     * Returns a List of all the Clubs
     *
     * @return LIst of Clubs. If no clubs, empty list.
     */
    List<Club> findAll();

    /**
     * REturns the club with the given primary key.
     *
     * @param clubid The primary key (long) of hte club you seek.
     * @return The given Club or throws an exception if not found.
     */
    Club findClubById(Long clubid);

    /**
     * Given a complete club object, saves that club object in the database. Only a Role with 'ADMIN' can save a club.
     * If a primary key is provided the record is completely replaced.
     * If no primary key is provided, one is automatically generated and the record is added to the databse by an ADMIN
     * @param club the club object to be saved
     * @return the saved club object including any automatically generated fields.
     */

    Club save(Club club);

    /**
     * Updates the provided fields in the club record provided by reference with the primary key.
     * @param updateClub The club fields to be updated.
     * @param clubid the primary key (long) of the club to update
     */
    void update(Club updateClub, long clubid);

    /**
     * Deletes the record by club (primary key) from the database.
     * @param clubid
     */
    void delete(long clubid);
}
