package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the clubactivties table.
 * The join table between clubs and activities.
 * <p>
 * Table enforces a unique constraint of the combination of clubid and activityid.
 * These two together form the primary key.
 * <p>
 * When you have a compound primary key, you must implement Serializable for Hibernate
 * When you implement Serializable you must implement equals and hash code
 */
@Entity(name = "ClubActivity")
@Table(name = "club_activites")
@IdClass(ClubActivitiesId.class)
public class ClubActivities extends Auditable implements Serializable
{
    @EmbeddedId
    private ClubActivitiesId id;

    /**
     * 1/2 of the primary key (long) for clubactivities.
     * Also is a foreign key into the club table
     */
    @ManyToOne
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = "roles", allowSetters = true)
    private Club club;
    /**
    * 1/2 of the primary key (long) for clubactivities.
    * Also is a foreign key into the activity table
    */
    @ManyToOne
    @MapsId("activityid")
    @JsonIgnoreProperties(value = "roles", allowSetters = true)
    private Activity activity;
    /**
     * Part of the join relationship between member and reactions
     * connects clubactivities to the member reactions combination
     */
    @Column
    @OneToMany
    @JoinColumns({@JoinColumn(name ="memberid"), @JoinColumn(name ="reactionid")})
    private Set<MemberReactions> reactions = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public ClubActivities() {
    }
    /**
     * Given the params, create a new club activity combination object
     *
     * @param club The club object of this relationship
     * @param activity The activity object of this relationship
     */
    public ClubActivities(
            Club club,
            Activity activity)
    {
        this.club = club;
        this.activity = activity;
        this.id = new ClubActivitiesId(club.getClubid(), activity.getActivityid());
    }

    /**
     * The getter for Club
     *
     * @return the complete user object associated with club activity combination
     */
    public Club getClub() {
        return club;
    }
    /**
     * Setter for club
     *
     * @param club change the club object associated with this club activity combination to this one.
     */
    public void setClub(Club club) {
        this.club = club;
    }
    /**
     * The getter for Activity
     *
     * @return the complete activity object associated with club activity combination
     */
    public Activity getActivity() {
        return activity;
    }
    /**
     * Setter for activity
     *
     * @param activity change the activity object associated with this club activity combination to this one.
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    /**
     * Getter for clubactivitiesid
     *
     * @return the id (long) of the clubactivitiesid
     */
    public ClubActivitiesId getId() {
        return id;
    }
    /**
     * Setter for id. Used primary for seeding data
     *
     * @param id the new id (long) of the clubactivities
     */
    public void setId(ClubActivitiesId id) {
        this.id = id;
    }
    /**
     * Getter for member reaction combinations
     *
     * @return A list of member reaction combinations associated with this clubactivity
     */
    public Set<MemberReactions> getReactions() {
        return reactions;
    }
    /**
     * Setter for club activity combinations
     *
     * @param reactions Change the list of member reaction combinations associated with this clubactivities to this one
     */
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
