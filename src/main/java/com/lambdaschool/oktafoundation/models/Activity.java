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
    @JsonIgnoreProperties(value = "club", allowSetters = true)
    private Set<ClubActivity> club = new HashSet<>();

    @Column(nullable = false)
    private String activityname;

    public long getActivityid() {
        return activityid;
    }

    public void setActivityid(long activityid) {
        this.activityid = activityid;
    }

    public Set<ClubActivity> getClub() {
        return club;
    }

    public void setClub(Set<ClubActivity> club) {
        this.club = club;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }
}
