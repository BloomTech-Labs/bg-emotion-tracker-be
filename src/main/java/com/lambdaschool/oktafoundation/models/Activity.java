package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activities")
public class Activity extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityid;

    @OneToMany(mappedBy = "activity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "activity", allowSetters = true)
    private Set<ClubActivities> clubs = new HashSet<>();


    @Column(nullable = false)
    private String activityname;

    public Activity() {
    }

    public Activity(String activityname) {
        this.activityname = activityname;
    }

    public long getActivityid() {
        return activityid;
    }

    public void setActivityid(long activityid) {
        this.activityid = activityid;
    }

    public Set<ClubActivities> getClub() {
        return clubs;
    }

    public void setClub(Set<ClubActivities> club) {
        this.clubs = club;
    }

    public Set<ClubActivities> getClubs() {
        return clubs;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }
}
