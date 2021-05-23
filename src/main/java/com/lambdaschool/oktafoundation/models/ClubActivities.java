package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.oktafoundation.repository.ClubActivityRepository;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "club_activities")
@IdClass(ClubActivitiesId.class)
public class ClubActivities extends Auditable implements Serializable
{
    /**
     * 1/2 of the primary key (long) for clubactivities.
     * Also is a foreign key into the clubs table
     */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = {"activities","activity","users","members"}, allowSetters = true)
    private Club club;

    /**
    * 1/2 of the primary key (long) for clubactivities.
    * Also is a foreign key into the activities table
    */
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "activityid")
    @JsonIgnoreProperties(value = {"clubs","club"}, allowSetters = true)
    private Activity activity;

    /**
     * Part of the join relationship between Member and Reaction
     * Connects clubactivities to the member reactions combination
     */
    @Column
    @OneToMany(
            mappedBy = "clubactivity",
            cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumns({@JoinColumn(name=""), @JoinColumn(name ="")})
    //first join column used to be memberid, but regardless what to put there, we get a null field in memberreacctions
    //however, this doesn't break anything, but if this can be fixed that'll be good.
    private Set<MemberReactions> reactions = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public ClubActivities() {
    }

    /**
     * Given the params, create a new club activity combination object
     *
     * @param club The Club object of this relationship
     * @param activity The Activity object of this relationship
     */
    public ClubActivities(

            Club club,
            Activity activity)
    {

        this.club = club;
        this.activity = activity;

    }

    /**
     * Given the params, create a new club activities combination object
     *
     * @param club The Club object of this relationship
     * @param activity The Activity object of this relationship
     * @param reactions The MemberReactions combination object associated with this relationship
     */
    public ClubActivities(Club club, Activity activity, Set<MemberReactions> reactions) {
        this.club = club;
        this.activity = activity;
        this.reactions = reactions;

    }

    /**
     * The getter for Club
     *
     * @return The complete Club object associated with this club activity combination
     */
    public Club getClub() {
        return club;

    }

    /**
     * Setter for Club
     *
     * @param club Change the Club object associated with this club activity combination to this one.
     */
    public void setClub(Club club) {
        this.club = club;
    }

    /**
     * The getter for Activity
     *
     * @return The complete Activity object associated with this club activity combination
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Setter for Activity
     *
     * @param activity Change the Activity object associated with this club activity combination to this one.
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Getter for member reaction combinations
     *
     * @return A list of member reaction combinations associated with this club activity combination
     */
    public Set<MemberReactions> getReactions() {
        return reactions;
    }

    /**
     * Setter for club activity combinations
     *
     * @param reactions Change the list of member reaction combinations associated with this club activity combination to this one
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

