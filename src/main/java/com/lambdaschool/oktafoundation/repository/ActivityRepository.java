package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The CRUD repository connecting Activity to the rest of the application
 */
public interface ActivityRepository extends CrudRepository<Activity, Long> {
   /**
    * Finds an Activity with the given name.
    *
    * @param name The name (String) of the Activity you seek
    * @return The first Activity object with the given name
    */

   Optional<Activity> findActivityByActivitynameLike(String name);
}
