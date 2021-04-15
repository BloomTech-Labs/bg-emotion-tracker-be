package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clubactivites")
public class ClubActivity extends Auditable implements Serializable
{

    // Joining club and activities and using both ids for the external ids
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clubactivityid;

    @ManyToOne
    @JoinColumn(name = "clubid")
    @JsonIgnoreProperties(value = "roles", allowSetters = true)
    private Club club;

    @Id
    @ManyToOne
    @JoinColumn(name = "activityid")
    @JsonIgnoreProperties(value = "roles", allowSetters = true)
    private Activity activity;

    public ClubActivity(
            Club club,
            Activity activity)
    {
        this.club = club;
        this.activity = activity;
    }

    // Constructor

    public ClubActivity() {

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

}
