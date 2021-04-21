package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "Activities", description = "Activities available")
@Entity
@Table(name = "activities")
public class Activity extends Auditable
{

    @ApiModelProperty(name = "Activity Id",
            value = "Primary Key for Activity",
            required = true,
            example = "1"
    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityid;


    @OneToMany(mappedBy = "activity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "activity", allowSetters = true)
    private Set<ClubActivities> clubs = new HashSet<>();

    @ApiModelProperty(name = "Activity Name",
            value = "Full activity name",
            required = true,
            example = "Piano Lessons"
    )

    @Column(nullable = false)
    private String activityname;

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

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }
}
