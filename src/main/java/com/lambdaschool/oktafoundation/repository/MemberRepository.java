package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting Member to the rest of the application
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
   /**
    * Findas the Member with the given id.
    *
    * @param memberid The id (String) of the Member you seek
    * @return The Member object with the given id
    */
   Optional<Member> findMemberByMemberid(String memberid);
}
