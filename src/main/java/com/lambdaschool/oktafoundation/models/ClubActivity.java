package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "clubactivites")
public class ClubActivity extends Auditable implements Serializable
{
    // What do we need an ID here for?
    @Id
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

    public ClubActivity() {
        
    }

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
