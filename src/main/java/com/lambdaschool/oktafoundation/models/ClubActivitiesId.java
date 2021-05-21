package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for ClubActivities
 */
@Embeddable
public class ClubActivitiesId implements Serializable {
    /**
     * The id of the Club
     */
    private Long club;

    /**
     * The id of the Activity
     */
    private Long activity;

    /**
     * The default constructor required by JPA
     */
    public ClubActivitiesId() {
    }

    /**
     * Getter for the club id
     *
     * @return The club id (long)
     */
    public long getClub() {
        return club;
    }

    /**
     * Setter for the club id
     *
     * @return The new club id for this object
     */
    public void setClub(long club) {
        this.club = club;
    }

    /**
     * Getter for the activity id
     *
     * @return The activity id (long)
     */
    public long getActivity() {
        return activity;
    }

    /**
     * Setter for the activity id
     *
     * @return The new activity id for this object
     */
    public void setActivity(long activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubActivitiesId that = (ClubActivitiesId) o;
        return getClub() == that.getClub() && getActivity() == that.getActivity();
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
