package com.lambdaschool.oktafoundation.models;

import java.io.Serializable;

public class ClubUsersId implements Serializable {
    private long club;
    private long users;

    public ClubUsersId() {
    }

    public ClubUsersId(long user, long role) {
        this.club = user;
        this.users = role;
    }

    public long getClub() {
        return club;
    }

    public void setClub(long club) {
        this.club = club;
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(long users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubUsersId that = (ClubUsersId) o;
        return getClub() == that.getClub() && getUsers() == that.getUsers();
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
