package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
   Optional<Member> findMemberByMemberid(String memberid);
}
