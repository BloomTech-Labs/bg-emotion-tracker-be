package com.lambdaschool.oktafoundation.views;

import java.util.List;

public class ClubActivityFeedback {
    long clubid;
    String clubname;
    List<ActivityReactionRating> activityReactionRatings;

    public ClubActivityFeedback() {
    }

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public List<ActivityReactionRating> getActivityReactionRatings() {
        return activityReactionRatings;
    }

    public void setActivityReactionRatings(List<ActivityReactionRating> activityReactionRatings) {
        this.activityReactionRatings = activityReactionRatings;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }
}

