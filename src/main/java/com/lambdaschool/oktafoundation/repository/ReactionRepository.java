package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Reaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting Reaction to the rest of the application
 */
public interface ReactionRepository extends CrudRepository<Reaction, Long> {
   /**
    * Finds a Reaction with the given reactionvalue.
    *
    * @param value The value (String) of the Reaction you seek
    * @return The first Reaction object with the value you seek
    */
   Optional<Reaction> findReactionByReactionvalue(String value);

   Optional<Reaction> findReactionByReactionid(long reactionId);
}
