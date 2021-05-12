package com.lambdaschool.oktafoundation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.io.Serializable;

/**
 * The entity allowing interaction with the memberreactions table.
 * The join table between member and activity.
 * <p>
 * Table enforces a unique constraint of the combination of member_table_id and reactionid.
 * These two together form the primary key.
 * <p>
 * When you have a compound primary key, you must implement Serializable for Hibernate
 * When you implement Serializable you must implement equals and hash code
 */
@Entity(name = "MemberReaction")
@Table(name = "memberreactions")
//@IdClass(MemberReactionsId.class)
public class MemberReactions extends Auditable implements Serializable
{
    /**
     * 1/2 of the primary key (long) for memberreactions.
     * Also is a foreign key into the member table
     */

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private long memberreactionid;


    @ManyToOne
    @JoinColumn(name="member_table_id")
    @JsonIgnoreProperties(value="reactions", allowSetters = true)
    private Member member;
    /**
     * 1/2 of the primary key (long) for memberreactions.
     * Also is a foreign key into the reaction table
     */

    @ManyToOne
    @JoinColumn(name = "reactionid")
    @JsonIgnoreProperties(value="member", allowSetters = true)
    private Reaction reaction;
    /**
     * The checkedin (Boolean). Cannot be null and must be unique
     */
    @Column(nullable = false)
    private Boolean checkedin;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "activityid"),
            @JoinColumn(name="clubid")})
    @JsonIgnoreProperties(value="reactions",allowSetters = true)
    private ClubActivities clubactivity;
    /**
     * Default constructor used primarily by the JPA.
     */
    public MemberReactions() {
    }
    /**
     * Given the params, create a new club activity combination object
     *
     * @param member The club object of this relationship
     * @param reaction The activity object of this relationship
     * @param member The club object of this relationship
     */


    public MemberReactions(Member member, Reaction reaction, Boolean checkedin, ClubActivities clubactivity) {
        this.member = member;
        this.reaction = reaction;
        this.checkedin = checkedin;
        this.clubactivity = clubactivity;
    }
    /**
     * The getter for Member
     *
     * @return the complete member object associated with member reaction combination
     */
    public Member getMember() {
        return member;
    }
    /**
     * Setter for Member
     *
     * @param member change the member object associated with this member reaction combination to this one.
     */
    public void setMember(Member member) {
        this.member = member;
    }
    /**
     * The getter for Reaction
     *
     * @return the complete reaction object associated with member reaction combination
     */
    public Reaction getReaction() {
        return reaction;
    }
    /**
     * Setter for Reaction
     *
     * @param reaction change the reaction object associated with this member reaction combination to this one.
     */
    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }
    /**
     * The getter for Checkedin
     *
     * @return the checkedin (Boolean) of the MemberReaction
     */
    public Boolean getCheckedin() {
        return checkedin;
    }
    /**
     * Setter for checkedin. Used primary for seeding data
     *
     * @param checkedin the new checkedin (Boolean) of the MemberReaction
     */
    public void setCheckedin(Boolean checkedin) {
        this.checkedin = checkedin;
    }

    public ClubActivities getClubactivity() {
        return clubactivity;
    }

    public void setClubactivity(ClubActivities clubactivity) {
        this.clubactivity = clubactivity;
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
