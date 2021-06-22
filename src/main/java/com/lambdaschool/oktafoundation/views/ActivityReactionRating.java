package com.lambdaschool.oktafoundation.views;

public class ActivityReactionRating {

    long activityid;
    String activityname;
    double activityrating;

    public ActivityReactionRating() {
    }

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

    public double getActivityrating() {
        return activityrating;
    }

    public void setActivityrating(double activityrating) {
        this.activityrating = activityrating;
    }
}
