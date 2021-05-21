package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.MemberReactions;

import java.util.List;

/**
 * The Service that works with MemberReaction Model.
 */
public interface MemberReactionService {
    /**
     * Returns a list of all the MemberReactions.
     *
     * @return List of MemberReactions; if no MemberReactions, empty list
     */
    List<MemberReactions> findAll();

    /**
     * Returns the MemberReaction with the given primary key.
     *
     * @param id The primary key (long) of the MemberReaction you seek
     * @return The given MemberReaction or throws an exception if not found
     */
    MemberReactions findMemberReactionById(Long id);
}
