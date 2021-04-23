package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.MemberReactions;
import org.springframework.data.repository.CrudRepository;

public interface MemberReactionRepository extends CrudRepository<MemberReactions, Long> {
}
