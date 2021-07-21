package com.lambdaschool.oktafoundation.views;

import java.util.List;

public class ActivityReaction {
    List<Double> reactionints;
    long activityid;
    String activityname;

    public ActivityReaction() {
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

    public List<Double> getReactionints() {
        return reactionints;
    }

    public void setReactionints(List<Double> reactionints) {
        this.reactionints = reactionints;
    }
}
