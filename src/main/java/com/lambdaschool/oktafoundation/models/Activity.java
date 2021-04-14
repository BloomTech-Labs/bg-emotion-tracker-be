package com.lambdaschool.oktafoundation.models;

import javax.persistence.*;

@Entity
@Table
public class Activity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long activityid;

    @Column(nullable = false) //unique = true? Not sure since one activity could be available across multi clubs
    private String activityname;

    public long getActivityid() {
        return activityid;
    }

    public void setActivityid(long activityid) {
        this.activityid = activityid;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }
}
