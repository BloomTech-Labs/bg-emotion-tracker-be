package com.lambdaschool.oktafoundation.services;

import com.lambdaschool.oktafoundation.models.User;

import java.util.List;

/**
 * The Service that works with User Model.
 * <p>
 * Note: Emails are added through the add user process
 * Roles are added through the add user process
 * No way to delete an assigned role
 */
public interface UserService
{
    /**
     * Returns a list of all the Users.
     *
     * @return List of Users; if no Users, empty list
     */
    List<User> findAll();

    /**
     * Returns a list of all Users whose username contains the given substring.
     *
     * @param username The substring (String) of the username of the Users you seek
     * @return List of Users whose username contains the given substring
     */
    List<User> findByNameContaining(String username);

    /**
     * Returns the User with the given primary key.
     *
     * @param id The primary key (long) of the User you seek
     * @return The given User or throws an exception if not found
     */
    User findUserById(long id);

    /**
     * Returns the User with the given name.
     *
     * @param name The full name (String) of the User you seek
     * @return The User with the given name or throws an exception if not found
     */
    User findByName(String name);

    /**
     * Deletes the User record and its Useremail items from the database based off of the provided primary key
     *
     * @param id id The primary key (long) of the User you seek
     */
    void delete(long id);

    /**
     * Given a complete user object, saves that User object in the database.
     * If a primary key is provided, the record is completely replaced.
     * If no primary key is provided, one is automatically generated and the record is added to the database.
     *
     * @param user The User object to be saved
     * @return The saved User object including any automatically generated fields
     */
    User save(User user);

    /**
     * Updates the provided fields in the User record referenced by the primary key.
     * <p>
     * Regarding Role and Useremail items, this process only allows adding those. Deleting and editing those lists
     * is done through a separate endpoint.
     *
     * @param user An object containing just the User fields to be updated
     * @param id   The primary key (long) of the User to update
     * @return The complete User object that was updated
     */
    User update(
        User user,
        long id);

    /**
     * Deletes all Users and their associated records from the database.
     */
    public void deleteAll();
}