package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the roles table.
 */
@Entity
@Table(name = "roles")
public class Role
    extends Auditable
{
    /**
     * The primary key (long) of the roles table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleid;

    /**
     * The name (String) of the role. Cannot be null and must be unique.
     */
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * Part of the join relationship between User and Role
     * connects roles to the user role combination
     */
    @OneToMany(mappedBy = "role",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "role",
        allowSetters = true)
    private Set<UserRoles> users = new HashSet<>();

    /**
     * Default Constructor used primarily by the JPA.
     */
    public Role()
    {
    }

    /**
     * Given the name, create a new Role object. User gets added later
     *
     * @param name the name of the Role in uppercase
     */
    public Role(String name)
    {
        this.name = name.toUpperCase();
    }

    /**
     * Getter for role id
     *
     * @return The role id, primary key, (long) of this Role
     */
    public long getRoleid()
    {
        return roleid;
    }

    /**
     * Setter for role id, used for seeding data
     *
     * @param roleid The new role id, primary key, (long) for this Role
     */
    public void setRoleid(long roleid)
    {
        this.roleid = roleid;
    }

    /**
     * Getter for role name
     *
     * @return The role name (String) in uppercase
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for role name
     *
     * @param name The new role name (String) for this Role, in uppercase
     */
    public void setName(String name)
    {
        this.name = name.toUpperCase();
    }

    /**
     * Getter for user role combinations
     *
     * @return A list of user role combinations associated with this Role
     */
    public Set<UserRoles> getUsers()
    {
        return users;
    }

    /**
     * Setter for user role combinations
     *
     * @param users Change the list of user role combinations associated with this Role to this one
     */
    public void setUsers(Set<UserRoles> users)
    {
        this.users = users;
    }
}
