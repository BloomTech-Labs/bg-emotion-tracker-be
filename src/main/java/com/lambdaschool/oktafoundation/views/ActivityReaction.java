package com.lambdaschool.oktafoundation.views;

import java.util.List;

public class ActivityReaction {
    List<Integer> reactionints;
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

    public List<Integer> getReactionints() {
        return reactionints;
    }

    public void setReactionints(List<Integer> reactionints) {
        this.reactionints = reactionints;
    }
}
