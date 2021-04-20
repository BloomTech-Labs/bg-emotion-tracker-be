package com.lambdaschool.oktafoundation.models;

import java.io.Serializable;

public class ClubUsersId implements Serializable {
    private long club;
    private long user;

    public ClubUsersId() {
    }

    public ClubUsersId(long user, long role) {
        this.club = user;
        this.user = role;
    }

    public long getClub() {
        return club;
    }

    public void setClub(long club) {
        this.club = club;
    }

    public long getUser() {
        return user;
    }

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
