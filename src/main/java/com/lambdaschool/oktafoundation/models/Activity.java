package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the activities table
 */
@ApiModel(value = "Activities", description = "Activities available")
@Entity
@Table(name = "activities")
public class Activity extends Auditable
{
    /**
     * The primary key (long) of the activities table.
     */
    @ApiModelProperty(name = "Activity Id",
            value = "Primary Key for Activity",
            required = true,
            example = "1"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityid;

    /**
     * The activityname (String). Cannot be null
     */
    @ApiModelProperty(name = "Activity Name",
            value = "Full activity name",
            required = true,
            example = "Piano Lessons"
            )
    @Column(nullable = false)
    private String activityname;

    /**
     * Part of the join relationship between Club and Activities
     * connects clubs to the club activities combination
     */
    @ApiModelProperty(name = "Club Activity Join",
            value = "Join Table",
            required = true
    )
    @OneToMany(mappedBy = "activity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = {"activity","reactions","members"}, allowSetters = true)
    private Set<ClubActivities> clubs = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Activity() {
    }

    /**
     * Given the params, create a new Activity object
     *
     * @param activityname The activityname (String) of the Activity
     */
    public Activity(String activityname) {
        this.activityname = activityname;
    }

    /**
     * Getter for activityid
     *
     * @return The activityid (long) of the Activity
     */
    public long getActivityid() {
        return activityid;
    }

    /**
     * Setter for activityid. Used primarily for seeding data
     *
     * @param activityid The new activityid (long) of the Activity
     */
    public void setActivityid(long activityid) {
        this.activityid = activityid;
    }

    /**
     * Setter for user role combinations
     *
     * @return A list of club activity combinations associated with this Activity
     */
    public void setClub(Set<ClubActivities> club) {
        this.clubs = club;
    }

    /**
     * Getter for club activity combinations
     *
     * @return A list of club activity combinations associated with this Activity
     */
    public Set<ClubActivities> getClubs() {
        return clubs;
    }

    /**
     * Getter for activityname
     *
     * @return The activityname (String) of the Activity
     */
    public String getActivityname() {
        return activityname;
    }

    /**
     * Setter for activityname
     *
     * @param activityname The new activityname (String) of this Activity
     */
    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }
}
