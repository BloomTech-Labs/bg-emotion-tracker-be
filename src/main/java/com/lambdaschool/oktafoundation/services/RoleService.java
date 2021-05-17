package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.Role;

import java.util.List;

/**
 * The service that works with the Role Model.
 * <p>
 * Note: no method update Role name
 */
public interface RoleService
{
    /**
     * Returns a list of all the Roles.
     *
     * @return list of Roles; if no Roles, empty list
     */
    List<Role> findAll();

    /**
     * Returns the first Role matching the given primary key.
     *
     * @param id The primary key (long) of the Role you seek
     * @return The Role object you seek
     */
    Role findRoleById(long id);

    /**
     * Given a complete Role object, saves that Role object in the database.
     * If a primary key is provided, the record is completely replaced.
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     * <p>
     * Note that Users are not added to Roles through this process.
     *
     * @param role The Role object to be saved
     * @return The saved Role object including any automatically generated fields
     */
    Role save(Role role);

    /**
     * Returns the first Role object matching the given name.
     *
     * @param name The name (String) of the Role you seek
     * @return The Role object matching the given name
     */
    Role findByName(String name);

    /**
     * Deletes all Roles and their associated records from the database.
     */
    public void deleteAll();

    /**
     * Updates the name of the given Role.
     *
     * @param id   The primary key (long) of the Role you wish to update
     * @param role The Role object containing the new name - only Role's name can be updated through this process
     * @return The complete Role with the new name
     */
    Role update(
        long id,
        Role role);
}