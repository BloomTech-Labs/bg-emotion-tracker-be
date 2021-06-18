package com.lambdaschool.oktafoundation.views;

import java.util.List;

public class ClubActivityFeedbackReactions {
    long clubid;
    List<ActivityReaction> activityreactions;

    public ClubActivityFeedbackReactions() {
    }

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public List<ActivityReaction> getActivityreactions() {
        return activityreactions;
    }

    public void setActivityreactions(List<ActivityReaction> activityreactions) {
        this.activityreactions = activityreactions;
    }
}
