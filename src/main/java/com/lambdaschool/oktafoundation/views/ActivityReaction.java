package com.lambdaschool.oktafoundation.views;

import java.util.List;

public class ActivityReaction {
    long activityid;
    List<Integer> reactionints;

    public ActivityReaction() {
    }

    public long getActivityid() {
        return activityid;
    }

    public void setActivityid(long activityid) {
        this.activityid = activityid;
    }

    public List<Integer> getReactionints() {
        return reactionints;
    }

    public void setReactionints(List<Integer> reactionints) {
        this.reactionints = reactionints;
    }
}
