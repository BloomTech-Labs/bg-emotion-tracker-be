package com.lambdaschool.oktafoundation.views;


import java.util.HashMap;

public class ClubActivityReactionCounts {
    private String clubname;
    private String activityname;
    private HashMap<String,Integer> reactionCounts;

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public HashMap<String, Integer> getReactionCounts() {
        return reactionCounts;
    }

    public void setReactionCounts(HashMap<String, Integer> reactionCounts) {
        this.reactionCounts = reactionCounts;
    }


    public ClubActivityReactionCounts() {
    }


}


