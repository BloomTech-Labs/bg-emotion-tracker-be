package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.ClubMembers;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting ClubUsers to the rest of the application
 */
public interface ClubMembersRepository extends CrudRepository<ClubMembers, Long> {

    Optional<ClubMembers> findClubMembersByClub_ClubidAndMember_Memberid(Long cid, String memberid);
}
