package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The entity allowing interaction with the clubusers table.
 * The join table between clubs and users.
 * <p>
 * Table enforces a unique constraint of the combination of clubid and userid.
 * These two together form the primary key.
 * <p>
 * When you have a compound primary key, you must implement Serializable for Hibernate
 * When you implement Serializable you must implement equals and hash code
 */
@Entity
@Table(name = "clubusers")
@IdClass(ClubUsersId.class)
public class ClubUsers extends Auditable implements Serializable
{
    /**
     * 1/2 of the primary key (long) for clubusers.
     * Also is a foreign key into the clubs table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = {"users","activities"}, allowSetters = true)
    private Club club;

    /**
     * 1/2 of the primary key (long) for clubusers.
     * Also is a foreign key into the users table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "clubs", allowSetters = true)
    private User user;

    /**
     * Default constructor used primarily by the JPA.
     */
    public ClubUsers() {

    }

    /**
     * Given the params, create a new club user combination object
     *
     * @param user The User object of this relationship
     * @param club The Club object of this relationship
     */
    public ClubUsers(
            Club club,
            User user
    )
    {
        this.club = club;
        this.user = user;
    }

    /**
     * The getter for Club
     *
     * @return The complete Club object associated with club user combination
     */
    public Club getClub() {
        return club;
    }

    /**
     * Setter for Club
     *
     * @param club Change the Club object associated with this club user combination to this one
     */
    public void setClub(Club club) {
        this.club = club;
    }

    /**
     * The getter for User
     *
     * @return The complete User object associated with club user combination
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for User
     *
     * @param user Change the User object associated with this club user combination to this one
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ClubUsers that = (ClubUsers) o;

        return ((club == null) ? 0 : club.getClubid()) == ((that.club == null) ? 0 : that.club.getClubid()) &&
                ((user == null) ? 0 : user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid());
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
