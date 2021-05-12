package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Reaction;

import java.util.List;

public interface ReactionService {
    /**
     * Returns a list of all the Reactions
     * @return List of Reactions. If no reactions, empty list.
     */
    List<Reaction> findAll();

    /**
     * Returns the Reaction with the given primary key.
     *
     * @param id The primary key (long) of the reaction you're looking for.
     * @return The given Reaction or throws an exception if not found.
     */
    Reaction findReactionById(long id);

    /**
     * Given a complete reaction object, saves that Reaction object in the database.
     * If a primary key is provided, the record is completely replaced
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param reaction the Reaction object to be saved
     * @return the saved Reaction object including any automatically generated fields.
     */
    Reaction save(Reaction reaction);
}
