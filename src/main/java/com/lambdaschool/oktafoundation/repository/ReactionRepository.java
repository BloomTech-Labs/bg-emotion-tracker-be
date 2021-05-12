package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Reaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReactionRepository extends CrudRepository<Reaction, Long> {

   Optional<Reaction> findReactionByReactionvalue(String value);
}
