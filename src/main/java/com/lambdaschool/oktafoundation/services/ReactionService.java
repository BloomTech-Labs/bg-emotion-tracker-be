package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Reaction;

import java.util.List;

/**
 * The Service that works with Reaction Model.
 */
public interface ReactionService {
    /**
     * Returns a list of all the Reactions.
     *
     * @return List of Reactions; if no reactions, empty list
     */
    List<Reaction> findAll();

    /**
     * Returns the Reaction with the given primary key.
     *
     * @param id The primary key (long) of the Reaction you're looking for
     * @return The given Reaction or throws an exception if not found
     */
    Reaction findReactionById(long id);

    /**
     * Given a complete Reaction object, saves that Reaction object in the database.
     * If a primary key is provided, the record is completely replaced.
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param reaction The Reaction object to be saved
     * @return The saved Reaction object including any automatically generated fields
     */
    Reaction save(Reaction reaction);

    Reaction findReactionByReactionid(long reactionId);
}
