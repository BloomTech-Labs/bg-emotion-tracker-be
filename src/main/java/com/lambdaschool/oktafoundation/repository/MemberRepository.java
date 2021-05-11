package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
