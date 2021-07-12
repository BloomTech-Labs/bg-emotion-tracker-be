package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Member;

import java.util.List;

/**
 * The Service that works with Member Model.
 */
public interface MemberService {
    /**
     * Returns a list of all the Members.
     *
     * @return List of Members; if no members, empty list
     */
    List<Member> findAll();

    /**
     * Returns the Member with the given primary key.
     *
     * @param id The primary key (long) of the Member you're looking for
     * @return The given Member or throws an exception if not found
     */
    Member findMemberById(long id);

    Member findMemberByMemberId(String memberid);

    void deleteAll();
    /**
     * Given a complete Member object, saves that Member in the database.
     * If a primary key is provided, the record is completely replaced.
     * If no primary key is provided, one is automatically generated and the record is added.
     *
     * @param member The Member object to be saved
     * @return The saved Member object including any automatically generated fields
     */
    Member save(Member member);
    Member update(long memberid);
}
