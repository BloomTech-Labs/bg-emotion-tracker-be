package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberReactionsId implements Serializable {
    private long member;
    private long reaction;

    public MemberReactionsId() {
    }

    public MemberReactionsId(long member, long reaction) {
        this.member = member;
        this.reaction = reaction;
    }

    public long getMember() {
        return member;
    }

    public void setMember(long member) {
        this.member = member;
    }

    public long getReaction() {
        return reaction;
    }

    public void setReaction(long reaction) {
        this.reaction = reaction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberReactionsId that = (MemberReactionsId) o;
        return getMember() == that.getMember() && getReaction() == that.getReaction();
    }
    @Override
    public int hashCode() {
        return 22;
    }
}
