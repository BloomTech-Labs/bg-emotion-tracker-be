package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for ClubUsers
 */
@Embeddable
public class ClubUsersId implements Serializable {
    /**
     * The id of the club
     */
    private long club;
    /**
     * The id of the user
     */
    private long user;
    /**
     * The default constructor required by JPA
     */
    public ClubUsersId() {
    }

    /**
     * Getter for the club id
     *
     * @return long the club id
     */
    public long getClub() {
        return club;
    }
    /**
     * Setter for the club id
     *
     * @param club the new club id for this object
     */
    public void setClub(long club) {
        this.club = club;
    }
    /**
     * Getter for the user id
     *
     * @return long the user id
     */
    public long getUser() {
        return user;
    }
    /**
     * Setter for the user id
     *
     * @param user the new user id for this object
     */
    public void setUser(long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubUsersId that = (ClubUsersId) o;
        return getClub() == that.getClub() && getUser() == that.getUser();
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
