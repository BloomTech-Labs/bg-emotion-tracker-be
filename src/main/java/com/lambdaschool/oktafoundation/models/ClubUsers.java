package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clubusers")
@IdClass(ClubUsersId.class)
public class ClubUsers extends Auditable implements Serializable
{
    @Id
    @ManyToOne
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Club club;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "clubs", allowSetters = true)
    private User user;

    public ClubUsers(
            User user,
            Club club
    )
    {
        this.user = user;
        this.club = club;
    }

    public ClubUsers() {

    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return user;
    }

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
