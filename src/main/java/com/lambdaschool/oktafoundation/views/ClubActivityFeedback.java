package com.lambdaschool.oktafoundation.views;

public class ClubActivityFeedback {
    long activityid;
    long clubid;
    double activityrating;

    public ClubActivityFeedback() {
    }

    public long getActivityid() {
        return activityid;
    }

    public void setActivityid(long activityid) {
        this.activityid = activityid;
    }

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public double getActivityrating() {
        return activityrating;
    }

    public void setActivityrating(double activityrating) {
        this.activityrating = activityrating;
    }
}

