package com.lambdaschool.oktafoundation.views;


import java.util.HashMap;

public class MemberReactionCounts {
    private String memberid;

    private HashMap<String,Integer> reactionCounts;

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public HashMap<String, Integer> getReactionCounts() {
        return reactionCounts;
    }

    public void setReactionCounts(HashMap<String, Integer> reactionCounts) {
        this.reactionCounts = reactionCounts;
    }

    public MemberReactionCounts() {
    }


}


