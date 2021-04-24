package com.lambdaschool.oktafoundation.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Class to represent the complex primary key for MemberReactions
 */
@Embeddable
public class MemberReactionsId implements Serializable {
    /**
     * The id of the member
     */
    private long member;
    /**
     * The id of the reaction
     */
    private long reaction;

    /**
     * The default constructor required by JPA
     */
    public MemberReactionsId() {
    }

    public MemberReactionsId(long member, long reaction) {
        this.member = member;
        this.reaction = reaction;
    }
    /**
     * Getter for the member id
     *
     * @return long the member id
     */
    public long getMember() {
        return member;
    }
    /**
     * Setter for the member id
     *
     * @return long the member id
     */
    public void setMember(long member) {
        this.member = member;
    }
    /**
     * Getter for the reaction id
     *
     * @return long the reaction id
     */

    public long getReaction() {
        return reaction;
    }
    /**
     * Setter for the reaction id
     *
     * @return long the reaction id
     */
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
