package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.views.ClubsCheckInOutSummary;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Service that works with Club Model.
 */
public interface ClubService {

    /**
     * Returns a List of all the Clubs.
     *
     * @return List of Clubs; if no Clubs, empty list
     */
    List<Club> findAll();

    /**
     * Returns the Club with the given primary key.
     *
     * @param clubid The primary key (long) of the club you seek
     * @return The given Club or throws an exception if not found
     */
    Club findClubById(Long clubid);

    /**
     * Given a complete Club object, saves that Club object in the database. Only a User with Role 'ADMIN' can save a Club.
     * If a primary key is provided the record is completely replaced.
     * If no primary key is provided, one is automatically generated and the record is added to the database by an ADMIN.
     *
     * @param club The Club object to be saved
     * @return The saved Club object including any automatically generated fields
     */
    Club save(Club club);

    /**
     * Updates the provided fields in the Club record provided by reference with the primary key.
     * @param club An object containing the Club fields to be updated
     * @param clubid The primary key (long) of the Club to update
     */
    Club update(Club club, long clubid);

    /**
     * Deletes the record by Club (primary key) from the database.
     *
     * @param clubid
     */
    void delete(long clubid);

    /**
     * Deletes all Clubs and their associated records from the database.
     */
    void deleteAll();

    Club findClubByName(String name);

    List<ClubsCheckInOutSummary> getClubsCheckInOutSummary();


}
