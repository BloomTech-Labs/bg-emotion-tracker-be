package com.lambdaschool.oktafoundation.repository;

import com.lambdaschool.oktafoundation.models.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
