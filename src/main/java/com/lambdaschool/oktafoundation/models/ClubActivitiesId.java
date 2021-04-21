package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClubActivitiesId implements Serializable {
    private long club;
    private long activity;

    public ClubActivitiesId() {
    }

    public ClubActivitiesId(long user, long role) {
        this.club = user;
        this.activity = role;
    }

    public long getClub() {
        return club;
    }

    public void setClub(long club) {
        this.club = club;
    }

    public long getActivity() {
        return activity;
    }

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
