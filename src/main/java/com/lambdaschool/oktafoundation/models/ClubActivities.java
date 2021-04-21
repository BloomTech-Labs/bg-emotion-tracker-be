package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "ClubActivity")
@Table(name = "club_activites")
@IdClass(ClubActivitiesId.class)
public class ClubActivities extends Auditable implements Serializable
{

    // Joining club and activities and using both ids for the external ids
    @EmbeddedId
    private ClubActivitiesId id;

    @ManyToOne
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = "roles", allowSetters = true)
    private Club club;

    @ManyToOne
    @MapsId("activityid")
    @JsonIgnoreProperties(value = "roles", allowSetters = true)
    private Activity activity;

    @Column
    @OneToMany
    @JoinColumns({@JoinColumn(name ="memberid"), @JoinColumn(name ="reactionid")})
    private Set<MemberReactions> reactions = new HashSet<>();


    public ClubActivities(
            Club club,
            Activity activity)
    {
        this.club = club;
        this.activity = activity;
        this.id = new ClubActivitiesId(club.getClubid(), activity.getActivityid());
    }

    // Constructor

    public ClubActivities() {

    }

    // Getters + Setters

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ClubActivitiesId getId() {
        return id;
    }

    public void setId(ClubActivitiesId id) {
        this.id = id;
    }

    public Set<MemberReactions> getReactions() {
        return reactions;
    }

    public void setReactions(Set<MemberReactions> reactions) {
        this.reactions = reactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ClubActivities that = (ClubActivities) o;

        return ((club == null) ? 0 : club.getClubid()) == ((that.club == null) ? 0 : that.club.getClubid()) &&
                ((activity == null) ? 0 : activity.getActivityid()) == ((that.activity == null) ? 0 : that.activity.getActivityid());
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
