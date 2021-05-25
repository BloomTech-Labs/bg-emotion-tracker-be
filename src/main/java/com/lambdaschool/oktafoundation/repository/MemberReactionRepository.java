package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Activity;
import com.lambdaschool.oktafoundation.models.Club;
import com.lambdaschool.oktafoundation.models.Member;
import com.lambdaschool.oktafoundation.models.MemberReactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The CRUD repository connecting MemberReaction to the rest of the application
 */
public interface MemberReactionRepository extends CrudRepository<MemberReactions, Long> {


    /**
     * Find all memberReactions given the club activity.
     *
     * @param c The Club Object
     * @param a The Activity Object
     * @return The List of all MemberReactions given this Club-Activity
     */
    List<MemberReactions> getMemberReactionsByClubactivity_ClubAndClubactivity_Activity(Club c, Activity a);


    /**
     * Find all memberReactions given a member
     *
     * @param m The Member Object
     * @return The List of all MemberReactions given this member
     */
    List<MemberReactions> getMemberReactionsByMember(Member m);

}
