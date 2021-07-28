package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The entity allowing interaction with the memberreactions table.
 * The join table between members and activities.
 * <p>
 * Table enforces a unique constraint of the combination of member_table_id and reactionid.
 * These two together form the primary key.
 * <p>
 * When you have a compound primary key, you must implement Serializable for Hibernate
 * When you implement Serializable you must implement equals and hash code
 */
@Entity(name = "MemberReaction")
@Table(name = "memberreactions")
public class MemberReactions extends Auditable implements Serializable
{
    /**
     * The primary key (long) for memberreactions.
     */
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long memberreactionid;

    /**
     * The  reactionresolved(boolean) for memberreactions lets us know if there has been a negative emotion entered as a memeberreaction.
     */
    @Column(nullable = false)
    private boolean reactionresolved;

    /**
     * The member with which this member reaction combination is associated.
     */
    @ManyToOne
    @JoinColumn(name="member_table_id")
    @JsonIgnoreProperties(value="reactions", allowSetters = true)
    private Member member;

    /**
     * The reaction with which this member reaction combination is associated.
     */
    @ManyToOne
    @JoinColumn(name = "reactionid")
    @JsonIgnoreProperties(value="member", allowSetters = true)
    private Reaction reaction;

    /**
     * The club activity combination to which this member gave this reaction.
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "activityid"),
            @JoinColumn(name="clubid")})
    @JsonIgnoreProperties(value={"reactions"},allowSetters = true)
    private ClubActivities clubactivity;

    /**
     * Default constructor used primarily by the JPA.
     */
    public MemberReactions() {
    }

    /**
     * Given the params, create a new member reactions combination object
     *
     * @param member The Member object of this relationship
     * @param reaction The Reaction object of this relationship
     * @param clubactivity The club activity combination associated with this relationship
     */
    public MemberReactions(Member member, Reaction reaction, ClubActivities clubactivity) {
        this.member = member;
        this.reaction = reaction;
        this.clubactivity = clubactivity;
        this.reactionresolved = reaction.getReactionint() > 3;
    }

    /**
     * The getter for Member
     *
     * @return The complete Member object associated with this member reaction combination
     */
    public Member getMember() {
        return member;
    }

    public Date getCreateddate(){
        return this.createdDate;
    }
    /**
     * Setter for Member
     *
     * @param member Change the Member object associated with this member reaction combination to this one
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * The getter for Reaction
     *
     * @return The complete Reaction object associated with this member reaction combination
     */
    public Reaction getReaction() {
        return reaction;
    }

    /**
     * Setter for Reaction
     *
     * @param reaction Change the Reaction object associated with this member reaction combination to this one
     */
    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    /**
     * The getter for the club activity combination
     *
     * @return The club activity combination associated with this member reaction combination
     */
    public ClubActivities getClubactivity() {
        return clubactivity;
    }

    /**
     * The setter for the club activity combination
     *
     * @param clubactivity Change the club activity combination associated with this member reaction combination
     */
    public void setClubactivity(ClubActivities clubactivity) {
        this.clubactivity = clubactivity;
    }

    /**
     * The getter for the reactionresolved boolean
     *
     * @return The boolean associated with this member reaction combination
     * that lets us know if a negative emotion has been entered as a memberreaction and if it still needs to be resolved.
     */
    public boolean isReactionresolved() {
        return reactionresolved;
    }
    /**
     * The setter for reactionresolved
     *
     * @param reactionresolved Change the club activity combination associated with this member reaction combination
     */
    public void setReactionresolved(boolean reactionresolved) {
        this.reactionresolved = reactionresolved;
    }
     /**
     * The getter for the memberreactionid
     *
     * @return The memberreactionid associated with all other data
     */
    public long getMemberreactionid() {
        return memberreactionid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MemberReactions that = (MemberReactions) o;

        return ((member == null) ? 0 : member.getMemberid()) == ((that.member == null) ? 0 : that.member.getMemberid()) &&
                ((reaction == null) ? 0 : reaction.getReactionid()) == ((that.reaction == null) ? 0 : that.reaction.getReactionid()) &&
                clubactivity.equals(that.getClubactivity());
    }

    @Override
    public int hashCode() {
        return 22;
    }
}
