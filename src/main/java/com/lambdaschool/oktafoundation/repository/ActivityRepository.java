package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Activity;
import org.springframework.data.repository.CrudRepository;
/**
 * The CRUD repository connecting Activity to the rest of the application
 */
public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
