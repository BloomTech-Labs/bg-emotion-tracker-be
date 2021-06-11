package com.lambdaschool.oktafoundation.views;

import com.lambdaschool.oktafoundation.models.MemberReactions;

import java.util.ArrayList;

public class LeaderboardReactions {
    long clubid;
    ArrayList<Double> memberReactionRatings;

    public LeaderboardReactions() {
    }

    public long getClubid() {
        return clubid;
    }

    public void setClubid(long clubid) {
        this.clubid = clubid;
    }

    public ArrayList<Double> getMemberReactionRatings() {
        return memberReactionRatings;
    }

    public void setMemberReactionRatings(ArrayList<Double> memberReactionRatings) {
        this.memberReactionRatings = memberReactionRatings;
    }
}
