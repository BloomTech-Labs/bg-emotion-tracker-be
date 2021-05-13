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
     * Also is a foreign key into the club table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = {"users","activities"}, allowSetters = true)
    private Club club;
    /**
     * 1/2 of the primary key (long) for clubusers.
     * Also is a foreign key into the user table
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
     * @param user The user object of this relationship
     * @param club The club object of this relationship
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
     * @return the complete club object associated with club user combination
     */
    public Club getClub() {
        return club;
    }
    /**
     * Setter for club
     *
     * @param club change the club object associated with this club user combination to this one.
     */
    public void setClub(Club club) {
        this.club = club;
    }
    /**
     * The getter for User
     *
     * @return the complete user object associated with club user combination
     */
    public User getUser() {
        return user;
    }
    /**
     * Setter for user
     *
     * @param user change the user object associated with this club user combination to this one.
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
